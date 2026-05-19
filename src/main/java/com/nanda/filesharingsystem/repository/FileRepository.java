package com.nanda.filesharingsystem.repository;

import com.nanda.filesharingsystem.entity.StoredFile;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<StoredFile, Long> {
    List<StoredFile> findAllByOwner_UserId(Long ownerId);
}