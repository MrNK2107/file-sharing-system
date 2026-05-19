package com.nanda.filesharingsystem.service;

import com.nanda.filesharingsystem.entity.FileVersion;
import com.nanda.filesharingsystem.entity.Folder;
import com.nanda.filesharingsystem.entity.PermissionType;
import com.nanda.filesharingsystem.entity.SharePermission;
import com.nanda.filesharingsystem.entity.StorageUsage;
import com.nanda.filesharingsystem.entity.StoredFile;
import com.nanda.filesharingsystem.entity.User;
import com.nanda.filesharingsystem.repository.FileRepository;
import com.nanda.filesharingsystem.repository.FileVersionRepository;
import com.nanda.filesharingsystem.repository.FolderRepository;
import com.nanda.filesharingsystem.repository.SharePermissionRepository;
import com.nanda.filesharingsystem.repository.StorageUsageRepository;
import com.nanda.filesharingsystem.repository.UserRepository;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FileSharingService {

    private final UserRepository userRepository;
    private final FolderRepository folderRepository;
    private final FileRepository fileRepository;
    private final FileVersionRepository fileVersionRepository;
    private final SharePermissionRepository sharePermissionRepository;
    private final StorageUsageRepository storageUsageRepository;

    public FileSharingService(
            UserRepository userRepository,
            FolderRepository folderRepository,
            FileRepository fileRepository,
            FileVersionRepository fileVersionRepository,
            SharePermissionRepository sharePermissionRepository,
            StorageUsageRepository storageUsageRepository) {
        this.userRepository = userRepository;
        this.folderRepository = folderRepository;
        this.fileRepository = fileRepository;
        this.fileVersionRepository = fileVersionRepository;
        this.sharePermissionRepository = sharePermissionRepository;
        this.storageUsageRepository = storageUsageRepository;
    }

    public List<StoredFile> getFilesByOwner(long ownerId) {
        return fileRepository.findAllByOwner_UserId(ownerId);
    }

    public List<FileVersion> getFileVersions(long fileId) {
        return fileVersionRepository.findByFile_FileIdOrderByVersionNumberAsc(fileId);
    }

    public List<SharePermission> getSharePermissionsByFile(long fileId) {
        return sharePermissionRepository.findByFile_FileId(fileId);
    }

    public List<SharePermission> getSharePermissionsByUser(long userId) {
        return sharePermissionRepository.findBySharedWithUser_UserId(userId);
    }

    public User registerUser(String name, String email, double storageQuotaGB) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        long storageQuotaBytes = (long) (storageQuotaGB * 1024 * 1024 * 1024);
        user.setStorageQuota((double) storageQuotaBytes);
        user.setActive(true);
        return userRepository.save(user);
    }

    public Folder createFolder(long ownerId, String folderName, Long parentFolderId) {
        User owner = findUser(ownerId);
        Folder folder = new Folder();
        folder.setOwner(owner);
        folder.setFolderName(folderName);
        if (parentFolderId != null) {
            folder.setParentFolder(findFolder(parentFolderId));
        }
        return folderRepository.save(folder);
    }

    public StoredFile createFile(
            long ownerId,
            long folderId,
            String fileName,
            String fileType,
            long fileSize,
            String storagePath,
            long uploadedByUserId,
            String changeLog) {
        User owner = findUser(ownerId);
        Folder folder = findFolder(folderId);
        User uploadedBy = findUser(uploadedByUserId);

        StoredFile file = new StoredFile();
        file.setOwner(owner);
        file.setFolder(folder);
        file.setFileName(fileName);
        file.setFileType(fileType);
        file.setFileSize(fileSize);
        file = fileRepository.save(file);

        FileVersion version = new FileVersion();
        version.setFile(file);
        version.setVersionNumber(1);
        version.setFileSize(fileSize);
        version.setStoragePath(storagePath);
        version.setUploadedBy(uploadedBy);
        version.setChangeLog(changeLog);
        version = fileVersionRepository.save(version);

        file.setCurrentVersion(version);
        file.setFileSize(fileSize);
        file = fileRepository.save(file);

        refreshUsage(owner);
        return file;
    }

    public FileVersion addVersion(
            long fileId,
            long fileSize,
            String storagePath,
            long uploadedByUserId,
            String changeLog) {
        StoredFile file = findFile(fileId);
        User uploadedBy = findUser(uploadedByUserId);
        List<FileVersion> versions = fileVersionRepository.findByFile_FileIdOrderByVersionNumberAsc(fileId);
        int nextVersionNumber = versions.isEmpty() ? 1 : versions.get(versions.size() - 1).getVersionNumber() + 1;

        FileVersion version = new FileVersion();
        version.setFile(file);
        version.setVersionNumber(nextVersionNumber);
        version.setFileSize(fileSize);
        version.setStoragePath(storagePath);
        version.setUploadedBy(uploadedBy);
        version.setChangeLog(changeLog);
        version = fileVersionRepository.save(version);

        file.setCurrentVersion(version);
        file.setFileSize(fileSize);
        fileRepository.save(file);

        refreshUsage(file.getOwner());
        return version;
    }

    public SharePermission shareFile(long fileId, long sharedWithUserId, PermissionType permissionType) {
        StoredFile file = findFile(fileId);
        User sharedWith = findUser(sharedWithUserId);

        SharePermission permission = new SharePermission();
        permission.setFile(file);
        permission.setSharedWithUser(sharedWith);
        permission.setPermissionType(permissionType);
        return sharePermissionRepository.save(permission);
    }

    public StorageUsage getStorageUsage(long userId) {
        User user = findUser(userId);
        long usedStorage = fileRepository.findAllByOwner_UserId(userId)
                .stream()
                .mapToLong(file -> file.getFileSize() == null ? 0L : file.getFileSize())
                .sum();

        StorageUsage usage = storageUsageRepository.findByUser_UserId(userId).orElseGet(StorageUsage::new);
        usage.setUser(user);
        usage.setUsedStorage(usedStorage);
        usage.setLastUpdated(Instant.now());
        return storageUsageRepository.save(usage);
    }

    private User findUser(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
    }

    private Folder findFolder(long folderId) {
        return folderRepository.findById(folderId)
                .orElseThrow(() -> new IllegalArgumentException("Folder not found: " + folderId));
    }

    private StoredFile findFile(long fileId) {
        return fileRepository.findById(fileId)
                .orElseThrow(() -> new IllegalArgumentException("File not found: " + fileId));
    }

    private void refreshUsage(User user) {
        getStorageUsage(user.getUserId());
    }
}