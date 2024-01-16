package com.juneox.marketspace.app.facade;

import com.juneox.marketspace.domain.analysis.dto.MarketSpaceAnalyticsDto;
import com.juneox.marketspace.domain.exception.DataLoadFailureException;
import com.juneox.marketspace.domain.meta.cache.MetaCacheStore;
import com.juneox.marketspace.domain.meta.dto.MarketSpaceDto;
import com.juneox.marketspace.domain.meta.dto.MarketSpaceGroupDto;
import com.juneox.marketspace.domain.meta.dto.ServiceIndustryDto;
import com.juneox.marketspace.domain.meta.entity.MarketSpace;
import com.juneox.marketspace.domain.meta.entity.MarketSpaceGroup;
import com.juneox.marketspace.domain.meta.entity.ServiceIndustry;
import com.juneox.marketspace.domain.raw.dto.MarketSpaceRawData;
import com.juneox.marketspace.domain.raw.dto.FileMetaDto;
import com.juneox.marketspace.domain.raw.entity.FileMetaInfo;
import com.juneox.marketspace.service.analysis.MarketSpaceAnalyticsService;
import com.juneox.marketspace.service.meta.MarketSpaceGroupService;
import com.juneox.marketspace.service.meta.MarketSpaceService;
import com.juneox.marketspace.service.meta.ServiceIndustryService;
import com.juneox.marketspace.service.raw.FileMetaService;
import com.juneox.marketspace.utils.CsvUtils;
import com.juneox.marketspace.utils.FileUtils;
import com.juneox.marketspace.utils.HashUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MetaDataFacadeService {

    private final MarketSpaceGroupService marketSpaceGroupService;
    private final MarketSpaceService marketSpaceService;
    private final ServiceIndustryService serviceIndustryService;
    private final MarketSpaceAnalyticsService marketSpaceAnalyticsService;
    private final FileMetaService fileMetaService;

    public void initDataCache(){
        MetaCacheStore metaCacheStore = MetaCacheStore.getInstance();

        metaCacheStore.putAllYearAndQuarterCode(
                marketSpaceAnalyticsService.getDistinctYearAndQuarterCode()
        );

        metaCacheStore.putAllMarketSpaceGroup(
                marketSpaceGroupService.getMarketSpaceGroups()
        );

        metaCacheStore.putAllMarketSpace(
                marketSpaceService.getMarketSpaces()
        );

        metaCacheStore.putAllServiceIndustry(
                serviceIndustryService.getServiceIndustry()
        );
    }

    public long loadMarketSpaceDataFile(String directoryPath){
        long completeRowCount = 0L;

        //find directories file
        List<FileMetaInfo> fileMetas = fileMetaService.getFileMetas();
        Set<String> fileHashSet = new HashSet<>();

        if(!fileMetas.isEmpty()){
            fileHashSet = fileMetas.stream()
                    .map(p->p.getFileHashS256())
                    .collect(Collectors.toSet());
        }

        //check file duplicate with file Hash
        List<FileMetaDto> fileMetaDtoList = this.checkForDuplicateFiles(directoryPath, fileHashSet);

        if(!fileMetaDtoList.isEmpty()){
            for(FileMetaDto fileMetaDto: fileMetaDtoList){
                //file parsing
                List<MarketSpaceRawData> rawData = this.parseMarketSpaceCsv(fileMetaDto.getFilePath());

                //insert marketSpaceGroup
                Map<String, MarketSpaceGroup> marketSpaceGroupMap = marketSpaceGroupService.createBulkMarketSpaceGroup(
                        new ArrayList<>(MetaCacheStore.getInstance().getMarketSpaceGroupDtoMap().values()));

                //insert marketSpace
                Map<String, MarketSpace> marketSpaceMap = marketSpaceService.createBulkMarketSpace(
                        new ArrayList<>(MetaCacheStore.getInstance().getMarketSpaceDtoMap().values()));

                //insert serviceIndustry
                Map<String, ServiceIndustry> serviceIndustryMap = serviceIndustryService.createBulkServiceIndustry(
                        new ArrayList<>(MetaCacheStore.getInstance().getServiceIndustryDtoMap().values()));

                //insert
                List<MarketSpaceAnalyticsDto> marketSpaceAnalyticsDtos = rawData.stream()
                        .map(p-> MarketSpaceAnalyticsDto.builder()
                                .yearAndQuarterCode(p.getYearAndQuarterCode())
                                .marketSpaceGroup(marketSpaceGroupMap.get(p.getMarketSpaceGroupCode()))
                                .marketSpace(marketSpaceMap.get(p.getMarketSpaceCode()))
                                .serviceIndustry(serviceIndustryMap.get(p.getServiceIndustryCode()))
                                .storesNumber(p.getStoresNumber())
                                .similarIndustryStoreNumber(p.getSimilarIndustryStoreNumber())
                                .bizNewStoreRate(p.getBizNewStoreRate())
                                .bizNewStoreNumber(p.getBizNewStoreNumber())
                                .bizCloseStoreRate(p.getBizCloseStoreRate())
                                .bizCloseStoreNumber(p.getBizCloseStoreNumber())
                                .franchiseStoreNumber(p.getFranchiseStoreNumber())
                                .build())
                        .collect(Collectors.toList());

                marketSpaceAnalyticsService.createBulkMarketSpaceAnalytics(marketSpaceAnalyticsDtos);
                completeRowCount =+ marketSpaceAnalyticsDtos.size();

                log.debug("load data completed file: "+ fileMetaDto.getFileName());
                fileMetaService.createFileMeta(fileMetaDto);
            }
        }
        return completeRowCount;
    }

    //test 확인을 위해 private에서 public로 변경
    public List<MarketSpaceRawData> parseMarketSpaceCsv(String filePath){
        List<List<String>> records = CsvUtils.readCsv(filePath);

        List<MarketSpaceRawData> rawData = records.stream().skip(1)
                .map(row -> {
                    //cache yearAndQuarterCode
                    MetaCacheStore.getInstance().putYearAndQuarter(row.get(0));

                    //cache marketSpaceGroupDto
                    MetaCacheStore.getInstance().putMarketSpaceGroupDto(MarketSpaceGroupDto.builder()
                                    .marketSpaceGroupCode(row.get(1))
                                    .marketSpaceGroupCodeName(row.get(2)).build());

                    //cache marketSpaceDto
                    MetaCacheStore.getInstance().putMarketSpaceDto(MarketSpaceDto.builder()
                                    .marketSpaceCode(row.get(3))
                                    .marketSpaceCodeName(row.get(4)).build());

                    //cache serviceIndustryDto
                    MetaCacheStore.getInstance().putServiceIndustryDto(ServiceIndustryDto.builder()
                                    .serviceIndustryCode(row.get(5))
                                    .serviceIndustryCodeName(row.get(6)).build());

                    return MarketSpaceRawData.builder()
                            .yearAndQuarterCode(row.get(0))
                            .marketSpaceGroupCode(row.get(1))
                            .marketSpaceGroupCodeName(row.get(2))
                            .marketSpaceCode(row.get(3))
                            .marketSpaceCodeName(row.get(4))
                            .serviceIndustryCode(row.get(5))
                            .serviceIndustryCodeName(row.get(6))
                            .storesNumber(Integer.parseInt(row.get(7)))
                            .similarIndustryStoreNumber(Integer.parseInt(row.get(8)))
                            .bizNewStoreRate(Integer.parseInt(row.get(9)))
                            .bizNewStoreNumber(Integer.parseInt(row.get(10)))
                            .bizCloseStoreRate(Integer.parseInt(row.get(11)))
                            .bizCloseStoreNumber(Integer.parseInt(row.get(12)))
                            .franchiseStoreNumber(Integer.parseInt(row.get(13)))
                            .build();
                })
                .collect(Collectors.toList());

        return rawData;
    }

    private List<FileMetaDto> checkForDuplicateFiles(String directoryPath, Set<String> fileHashSet){
        List<Path> filePaths;
        List<FileMetaDto> fileMetaDtoList;
        try {
            filePaths = FileUtils.getFilePaths(directoryPath);
            fileMetaDtoList = this.getFileMetaDto(filePaths, fileHashSet);

            return fileMetaDtoList;
        } catch (IOException e) {
            throw new DataLoadFailureException(e);
        }
    }

    private List<FileMetaDto> getFileMetaDto(List<Path> filePaths, Set<String> fileHashSet){
        List<FileMetaDto> fileMetaDtos = filePaths.stream()
                .map(path -> {
                    String filePath = path.toString();
                    String fileName = path.getFileName().toString();
                    String hashValue;
                    try {
                        hashValue = HashUtils.getHashS256WithFile(path);
                    } catch (NoSuchAlgorithmException e) {
                        throw new DataLoadFailureException(e);
                    } catch (IOException e) {
                        throw new DataLoadFailureException(e);
                    }
                    return FileMetaDto.builder()
                            .fileName(fileName)
                            .fileHashS256(hashValue)
                            .filePath(filePath)
                            .build();
                })
                .filter(dto -> !fileHashSet.contains(dto.getFileHashS256()))
                .collect(Collectors.toList());

        return fileMetaDtos;
    }
}
