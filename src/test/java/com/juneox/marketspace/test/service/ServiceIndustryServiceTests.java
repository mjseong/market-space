package com.juneox.marketspace.test.service;

import com.juneox.marketspace.domain.meta.dto.ServiceIndustryDto;
import com.juneox.marketspace.domain.meta.entity.ServiceIndustry;
import com.juneox.marketspace.persistence.jpa.ServiceIndustryRepository;
import com.juneox.marketspace.service.meta.ServiceIndustryService;
import com.juneox.marketspace.service.meta.impl.ServiceIndustryServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class ServiceIndustryServiceTests {

    @Mock
    ServiceIndustryRepository serviceIndustryRepository;

    ServiceIndustryService serviceIndustryService;

    @BeforeEach
    void init(){
        this.serviceIndustryService = new ServiceIndustryServiceImpl(serviceIndustryRepository);
    }

    @Test
    void createDataTest(){
        //given
        ServiceIndustryDto dto = ServiceIndustryDto.builder()
                .serviceIndustryCode("")
                .serviceIndustryCodeName("")
                .build();

        ServiceIndustry serviceIndustry = ServiceIndustry.builder()
                .id(1L)
                .serviceIndustryCode("")
                .serviceIndustryCodeName("")
                .build();

        BDDMockito.given(serviceIndustryRepository.save(Mockito.any(ServiceIndustry.class))).willReturn(serviceIndustry);

        //when
        serviceIndustryService.createServiceIndustry(dto);

        //then
        Mockito.verify(serviceIndustryRepository).save(Mockito.any(ServiceIndustry.class));
    }

    @Test
    void createBulkDataTest(){
        //given
        List<ServiceIndustryDto> marketSpaceDtoList = Arrays.asList(
                ServiceIndustryDto.builder()
                        .serviceIndustryCode("CS300009")
                        .serviceIndustryCodeName("청과상")
                        .build(),
                ServiceIndustryDto.builder()
                        .serviceIndustryCode("CS300007")
                        .serviceIndustryCodeName("육류판매")
                        .build()
        );

        List<ServiceIndustry> marketSpaceList = Arrays.asList(
                ServiceIndustry.builder()
                        .id(1L)
                        .serviceIndustryCode("CS300009")
                        .serviceIndustryCodeName("청과상")
                        .build(),
                ServiceIndustry.builder()
                        .id(2L)
                        .serviceIndustryCode("CS300007")
                        .serviceIndustryCodeName("육류판매")
                        .build()
        );

        Map<String, ServiceIndustry> expectedMap = Map.of(
                "CS300009", ServiceIndustry.builder()
                        .serviceIndustryCode("CS300009")
                        .serviceIndustryCodeName("청과상")
                        .build(),
                "CS300007", ServiceIndustry.builder()
                        .serviceIndustryCode("CS300007")
                        .serviceIndustryCodeName("육류판매")
                        .build()
        );

        BDDMockito.given(serviceIndustryRepository.saveAll(Mockito.any())).willReturn(marketSpaceList);

        //when
        Map<String, ServiceIndustry> actualMap = serviceIndustryService.createBulkServiceIndustry(marketSpaceDtoList);

        //then
        Assertions.assertEquals(expectedMap.get("CS300009").getServiceIndustryCodeName(),
                actualMap.get("CS300009").getServiceIndustryCodeName());

    }
}
