package com.juneox.marketspace.domain.raw.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FileMetaDto {

    private String fileName;
    private String fileHashS256;
    private String filePath;
}
