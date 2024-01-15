package com.juneox.marketspace.service.raw;

import com.juneox.marketspace.domain.model.raw.dto.FileMetaDto;
import com.juneox.marketspace.domain.model.raw.entity.FileMetaInfo;

import java.util.List;

public interface FileMetaService {

    public void createFileMetas(List<FileMetaDto> fileMetaDtoList);
    public void createFileMeta(FileMetaDto fileMetaDto);
    public List<FileMetaInfo> getFileMetas();

}
