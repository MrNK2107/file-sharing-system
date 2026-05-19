package com.nanda.filesharingsystem;

import com.nanda.filesharingsystem.entity.FileVersion;
import com.nanda.filesharingsystem.entity.Folder;
import com.nanda.filesharingsystem.entity.PermissionType;
import com.nanda.filesharingsystem.entity.SharePermission;
import com.nanda.filesharingsystem.entity.StorageUsage;
import com.nanda.filesharingsystem.entity.StoredFile;
import com.nanda.filesharingsystem.entity.User;
import com.nanda.filesharingsystem.repository.FolderRepository;
import com.nanda.filesharingsystem.repository.SharePermissionRepository;
import com.nanda.filesharingsystem.repository.StorageUsageRepository;
import com.nanda.filesharingsystem.repository.UserRepository;
import com.nanda.filesharingsystem.service.FileSharingService;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class FilesharingsystemApplication implements CommandLineRunner {

    private final FileSharingService fileSharingService;
    private final UserRepository userRepository;
    private final FolderRepository folderRepository;
    private final SharePermissionRepository sharePermissionRepository;
    private final StorageUsageRepository storageUsageRepository;

    public FilesharingsystemApplication(
            FileSharingService fileSharingService,
            UserRepository userRepository,
            FolderRepository folderRepository,
            SharePermissionRepository sharePermissionRepository,
            StorageUsageRepository storageUsageRepository) {
        this.fileSharingService = fileSharingService;
        this.userRepository = userRepository;
        this.folderRepository = folderRepository;
        this.sharePermissionRepository = sharePermissionRepository;
        this.storageUsageRepository = storageUsageRepository;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(FilesharingsystemApplication.class);
        app.setWebApplicationType(org.springframework.boot.WebApplicationType.NONE);
        ConfigurableApplicationContext context = app.run(args);
        int exitCode = SpringApplication.exit(context);
        System.exit(exitCode);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Enter choice: ");
            System.out.flush();
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> createUser(scanner);
                case "2" -> viewUsers();
                case "3" -> createFolder(scanner);
                case "4" -> viewFolders(scanner);
                case "5" -> createFile(scanner);
                case "6" -> viewFiles(scanner);
                case "7" -> addFileVersion(scanner);
                case "8" -> viewFileVersions(scanner);
                case "9" -> shareFile(scanner);
                case "10" -> viewSharePermissions(scanner);
                case "11" -> viewStorageReport(scanner);
                case "12" -> running = false;
                default -> System.out.println("Invalid choice. Try again.");
            }

            System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("===== File Sharing System =====");
        System.out.println("1.  Create User");
        System.out.println("2.  View Users");
        System.out.println("3.  Create Folder");
        System.out.println("4.  View Folders");
        System.out.println("5.  Create File");
        System.out.println("6.  View Files");
        System.out.println("7.  Add File Version");
        System.out.println("8.  View File Versions");
        System.out.println("9.  Share File");
        System.out.println("10. View Share Permissions");
        System.out.println("11. View Storage Report");
        System.out.println("12. Exit");
    }

    private void createUser(Scanner scanner) {
        System.out.print("Enter name: ");
        System.out.flush();
        String name = scanner.nextLine().trim();

        System.out.print("Enter email: ");
        System.out.flush();
        String email = scanner.nextLine().trim();

        double quotaGB = readDouble(scanner, "Enter storage quota (GB): ");
        if (quotaGB == Double.MIN_VALUE) return;

        try {
            User user = fileSharingService.registerUser(name, email, quotaGB);
            System.out.println("User created: ID=" + user.getUserId() + ", Email=" + user.getEmail());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }
        for (User user : users) {
            System.out.printf("ID=%d | Name=%s | Email=%s | Quota=%.2f GB | Active=%s%n",
                    user.getUserId(), user.getName(), user.getEmail(), user.getStorageQuota() / (1024.0 * 1024 * 1024), user.isActive());
        }
    }

    private void createFolder(Scanner scanner) {
        long ownerId = readUserId(scanner, "Enter owner user ID: ");
        if (ownerId == Long.MIN_VALUE) return;

        System.out.print("Enter folder name: ");
        System.out.flush();
        String name = scanner.nextLine().trim();

        System.out.print("Enter parent folder ID (or press Enter to skip): ");
        System.out.flush();
        String parentInput = scanner.nextLine().trim();
        Long parentFolderId = null;
        if (!parentInput.isEmpty()) {
            try {
                parentFolderId = Long.parseLong(parentInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid folder ID.");
                return;
            }
        }

        try {
            Folder folder = fileSharingService.createFolder(ownerId, name, parentFolderId);
            System.out.println("Folder created: ID=" + folder.getFolderId() + ", Name=" + folder.getFolderName());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewFolders(Scanner scanner) {
        System.out.print("Enter owner user ID (or press Enter to view all): ");
        System.out.flush();
        String input = scanner.nextLine().trim();
        List<Folder> folders;
        if (input.isEmpty()) {
            folders = folderRepository.findAll();
        } else {
            try {
                long ownerId = Long.parseLong(input);
                folders = folderRepository.findByOwner_UserId(ownerId);
            } catch (NumberFormatException e) {
                System.out.println("Invalid user ID.");
                return;
            }
        }

        if (folders.isEmpty()) {
            System.out.println("No folders found.");
            return;
        }
        for (Folder folder : folders) {
            String parent = folder.getParentFolder() != null ? String.valueOf(folder.getParentFolder().getFolderId()) : "None";
            System.out.printf("ID=%d | Name=%s | OwnerID=%d | ParentID=%s%n",
                    folder.getFolderId(), folder.getFolderName(), folder.getOwner().getUserId(), parent);
        }
    }

    private void createFile(Scanner scanner) {
        long ownerId = readUserId(scanner, "Enter owner user ID: ");
        if (ownerId == Long.MIN_VALUE) return;

        long folderId = readFolderId(scanner, "Enter folder ID: ");
        if (folderId == Long.MIN_VALUE) return;

        System.out.print("Enter file name: ");
        System.out.flush();
        String fileName = scanner.nextLine().trim();

        System.out.print("Enter file type: ");
        System.out.flush();
        String fileType = scanner.nextLine().trim();

        long fileSize = readLong(scanner, "Enter file size (bytes): ");
        if (fileSize == Long.MIN_VALUE) return;

        System.out.print("Enter storage path: ");
        System.out.flush();
        String storagePath = scanner.nextLine().trim();

        long uploadedById = readUserId(scanner, "Enter uploaded-by user ID: ");
        if (uploadedById == Long.MIN_VALUE) return;

        System.out.print("Enter change log (or press Enter for default): ");
        System.out.flush();
        String changeLog = scanner.nextLine().trim();
        if (changeLog.isEmpty()) changeLog = "initial upload";

        try {
            StoredFile file = fileSharingService.createFile(ownerId, folderId, fileName, fileType, fileSize, storagePath, uploadedById, changeLog);
            System.out.println("File created: ID=" + file.getFileId() + " | Name=" + file.getFileName() + " | Version=" + file.getCurrentVersion().getVersionNumber());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewFiles(Scanner scanner) {
        System.out.print("Enter owner user ID (or press Enter to view all): ");
        System.out.flush();
        String input = scanner.nextLine().trim();
        List<StoredFile> files;
        if (input.isEmpty()) {
            files = fileSharingService.getFilesByOwner(0);
        } else {
            try {
                long ownerId = Long.parseLong(input);
                files = fileSharingService.getFilesByOwner(ownerId);
            } catch (NumberFormatException e) {
                System.out.println("Invalid user ID.");
                return;
            }
        }

        if (files.isEmpty()) {
            System.out.println("No files found.");
            return;
        }
        for (StoredFile file : files) {
            System.out.printf("ID=%d | Name=%s | Type=%s | Size=%d | OwnerID=%d | FolderID=%s | Version=%d%n",
                    file.getFileId(), file.getFileName(), file.getFileType(), file.getFileSize(),
                    file.getOwner().getUserId(),
                    file.getFolder() != null ? file.getFolder().getFolderId() : "None",
                    file.getCurrentVersion() != null ? file.getCurrentVersion().getVersionNumber() : 0);
        }
    }

    private void addFileVersion(Scanner scanner) {
        long fileId = readFileId(scanner, "Enter file ID: ");
        if (fileId == Long.MIN_VALUE) return;

        long fileSize = readLong(scanner, "Enter file size (bytes): ");
        if (fileSize == Long.MIN_VALUE) return;

        System.out.print("Enter storage path: ");
        System.out.flush();
        String storagePath = scanner.nextLine().trim();

        long uploadedById = readUserId(scanner, "Enter uploaded-by user ID: ");
        if (uploadedById == Long.MIN_VALUE) return;

        System.out.print("Enter change log (or press Enter for default): ");
        System.out.flush();
        String changeLog = scanner.nextLine().trim();
        if (changeLog.isEmpty()) changeLog = "version update";

        try {
            FileVersion version = fileSharingService.addVersion(fileId, fileSize, storagePath, uploadedById, changeLog);
            System.out.println("Version added: ID=" + version.getVersionId() + " | Version=" + version.getVersionNumber());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewFileVersions(Scanner scanner) {
        long fileId = readFileId(scanner, "Enter file ID: ");
        if (fileId == Long.MIN_VALUE) return;

        List<FileVersion> versions = fileSharingService.getFileVersions(fileId);
        if (versions.isEmpty()) {
            System.out.println("No versions found for this file.");
            return;
        }
        for (FileVersion version : versions) {
            System.out.printf("VersionID=%d | Number=%d | Size=%d | Path=%s | UploadedBy=%s | At=%s | Log=%s%n",
                    version.getVersionId(), version.getVersionNumber(), version.getFileSize(),
                    version.getStoragePath(), version.getUploadedBy().getEmail(),
                    version.getUploadedAt(), version.getChangeLog());
        }
    }

    private void shareFile(Scanner scanner) {
        long fileId = readFileId(scanner, "Enter file ID: ");
        if (fileId == Long.MIN_VALUE) return;

        long sharedWithId = readUserId(scanner, "Enter user ID to share with: ");
        if (sharedWithId == Long.MIN_VALUE) return;

        System.out.print("Enter permission (VIEW, DOWNLOAD, EDIT): ");
        System.out.flush();
        String permInput = scanner.nextLine().trim().toUpperCase();
        PermissionType permissionType;
        try {
            permissionType = PermissionType.valueOf(permInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid permission type.");
            return;
        }

        try {
            SharePermission permission = fileSharingService.shareFile(fileId, sharedWithId, permissionType);
            System.out.println("File shared: PermissionID=" + permission.getPermissionId() + " | Type=" + permission.getPermissionType());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewSharePermissions(Scanner scanner) {
        System.out.print("Enter file ID (or press Enter to view all): ");
        System.out.flush();
        String input = scanner.nextLine().trim();
        List<SharePermission> permissions;
        if (input.isEmpty()) {
            permissions = sharePermissionRepository.findAll();
        } else {
            try {
                long fileId = Long.parseLong(input);
                permissions = fileSharingService.getSharePermissionsByFile(fileId);
            } catch (NumberFormatException e) {
                System.out.println("Invalid file ID.");
                return;
            }
        }

        if (permissions.isEmpty()) {
            System.out.println("No share permissions found.");
            return;
        }
        for (SharePermission perm : permissions) {
            System.out.printf("ID=%d | FileID=%d | User=%s | Permission=%s%n",
                    perm.getPermissionId(),
                    perm.getFile().getFileId(),
                    perm.getSharedWithUser().getEmail(),
                    perm.getPermissionType());
        }
    }

    private void viewStorageReport(Scanner scanner) {
        long userId = readUserId(scanner, "Enter user ID: ");
        if (userId == Long.MIN_VALUE) return;

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            System.out.println("User not found.");
            return;
        }
        User user = userOpt.get();

        try {
            StorageUsage usage = fileSharingService.getStorageUsage(userId);
            System.out.println("User: " + user.getName() + " (" + user.getEmail() + ")");
            System.out.println("Used Storage: " + usage.getUsedStorage() + " bytes");
            System.out.println("Storage Quota: " + (user.getStorageQuota() / (1024 * 1024 * 1024)) + " GB");
            double quotaGB = user.getStorageQuota() / (1024.0 * 1024 * 1024);
            double usedGB = usage.getUsedStorage() / (1024.0 * 1024 * 1024);
            System.out.println("Used Storage: " + usedGB + " GB");
            long percent = user.getStorageQuota() > 0 ? (long) ((usage.getUsedStorage() * 100.0) / user.getStorageQuota()) : 0;
            System.out.println("Usage: " + percent + "%");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private long readUserId(Scanner scanner, String prompt) {
        System.out.print(prompt);
        System.out.flush();
        try {
            return Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid user ID.");
            return Long.MIN_VALUE;
        }
    }

    private long readFolderId(Scanner scanner, String prompt) {
        System.out.print(prompt);
        System.out.flush();
        try {
            return Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid folder ID.");
            return Long.MIN_VALUE;
        }
    }

    private long readFileId(Scanner scanner, String prompt) {
        System.out.print(prompt);
        System.out.flush();
        try {
            return Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid file ID.");
            return Long.MIN_VALUE;
        }
    }

    private long readLong(Scanner scanner, String prompt) {
        System.out.print(prompt);
        System.out.flush();
        try {
            return Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
            return Long.MIN_VALUE;
        }
    }

    private double readDouble(Scanner scanner, String prompt) {
        System.out.print(prompt);
        System.out.flush();
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
            return Double.MIN_VALUE;
        }
    }
}