package com.nanda.filesharingsystem.repository;

import com.nanda.filesharingsystem.entity.FileVersion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileVersionRepository extends JpaRepository<FileVersion, Long> {
    List<FileVersion> findByFile_FileIdOrderByVersionNumberAsc(Long fileId);
}