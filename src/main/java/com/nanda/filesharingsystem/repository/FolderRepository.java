package com.nanda.filesharingsystem.repository;

import com.nanda.filesharingsystem.entity.Folder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findByOwner_UserId(Long ownerId);
}