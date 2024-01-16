package com.juneox.marketspace.service.meta.impl;

import com.juneox.marketspace.domain.exception.NoSupportedException;
import com.juneox.marketspace.domain.meta.dto.ServiceIndustryDto;
import com.juneox.marketspace.domain.meta.entity.ServiceIndustry;
import com.juneox.marketspace.persistence.jpa.ServiceIndustryRepository;
import com.juneox.marketspace.service.meta.ServiceIndustryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ServiceIndustryServiceImpl implements ServiceIndustryService {

    private final ServiceIndustryRepository serviceIndustryRepository;

    @Transactional(readOnly = true)
    @Override
    public List<ServiceIndustry> getServiceIndustry() {
        return serviceIndustryRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ServiceIndustry createServiceIndustry(ServiceIndustryDto serviceIndustryDto) {

        ServiceIndustry serviceIndustry = ServiceIndustry.builder()
                .serviceIndustryCode(serviceIndustryDto.getServiceIndustryCode())
                .serviceIndustryCodeName(serviceIndustryDto.getServiceIndustryCodeName())
                .build();

        return serviceIndustryRepository.save(serviceIndustry);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Map<String, ServiceIndustry> createBulkServiceIndustry(List<ServiceIndustryDto> serviceIndustryDtos) {
        //bulk insert시에 중복 키 값 필터
        Map<String, ServiceIndustry> serviceIndustryMap = serviceIndustryRepository.findAll().stream()
                .collect(Collectors.toMap(p -> p.getServiceIndustryCode(), p1-> p1));

        List<ServiceIndustry> serviceIndustries = serviceIndustryDtos.stream()
                .filter(f -> !serviceIndustryMap.containsKey(f.getServiceIndustryCode()))
                .map(serviceIndustryDto -> ServiceIndustry.builder()
                        .serviceIndustryCode(serviceIndustryDto.getServiceIndustryCode())
                        .serviceIndustryCodeName(serviceIndustryDto.getServiceIndustryCodeName())
                        .build())
                .collect(Collectors.toList());
        serviceIndustryMap.putAll(
                serviceIndustryRepository.saveAll(serviceIndustries).stream()
                        .collect(Collectors.toMap(p -> p.getServiceIndustryCode(), p1 -> p1))
        );
        return serviceIndustryMap;
    }

    @Transactional
    @Override
    public ServiceIndustry modifyServiceIndustry() {
        throw new NoSupportedException();
    }

    @Transactional
    @Override
    public void deleteServiceIndustry(long id) {
        throw new NoSupportedException();
    }
}
