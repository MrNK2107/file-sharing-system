package com.nanda.filesharingsystem.repository;

import com.nanda.filesharingsystem.entity.StorageUsage;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorageUsageRepository extends JpaRepository<StorageUsage, Long> {
    Optional<StorageUsage> findByUser_UserId(Long userId);
}