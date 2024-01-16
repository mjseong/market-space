package com.juneox.marketspace.service.raw.impl;

import com.juneox.marketspace.domain.raw.dto.FileMetaDto;
import com.juneox.marketspace.domain.raw.entity.FileMetaInfo;
import com.juneox.marketspace.persistence.jpa.FileMetaRepository;
import com.juneox.marketspace.service.raw.FileMetaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FileMetaServiceImpl implements FileMetaService {

    private final FileMetaRepository fileMetaRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void createFileMetas(List<FileMetaDto> fileMetaDtoList){
        List<FileMetaInfo> fileMetaInfos = fileMetaDtoList.stream()
                .map(fileMetaDto -> FileMetaInfo.builder()
                        .fileName(fileMetaDto.getFileName())
                        .fileHashS256(fileMetaDto.getFileHashS256())
                        .build())
                .collect(Collectors.toList());

        fileMetaRepository.saveAll(fileMetaInfos);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void createFileMeta(FileMetaDto fileMetaDto) {
        FileMetaInfo fileMetaInfo = FileMetaInfo.builder()
                .fileName(fileMetaDto.getFileName())
                .fileHashS256(fileMetaDto.getFileHashS256())
                .build();

        fileMetaRepository.save(fileMetaInfo);
    }

    @Transactional(readOnly = true)
    @Override
    public List<FileMetaInfo> getFileMetas() {
        return fileMetaRepository.findAll();
    }
}
