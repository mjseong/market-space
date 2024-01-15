package com.juneox.marketspace.persistence.jpa;

import com.juneox.marketspace.domain.model.raw.entity.FileMetaInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileMetaRepository extends JpaRepository<FileMetaInfo, Long> {
}
