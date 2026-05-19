package com.nanda.filesharingsystem.repository;

import com.nanda.filesharingsystem.entity.SharePermission;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SharePermissionRepository extends JpaRepository<SharePermission, Long> {
    List<SharePermission> findByFile_FileId(Long fileId);
    List<SharePermission> findBySharedWithUser_UserId(Long userId);
}