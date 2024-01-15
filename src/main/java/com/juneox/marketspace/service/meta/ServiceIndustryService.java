package com.juneox.marketspace.service.meta;

import com.juneox.marketspace.domain.meta.dto.ServiceIndustryDto;
import com.juneox.marketspace.domain.meta.entity.ServiceIndustry;

import java.util.List;
import java.util.Map;

public interface ServiceIndustryService {

    public ServiceIndustry createServiceIndustry(ServiceIndustryDto serviceIndustryDto);
    public Map<String, ServiceIndustry> createBulkServiceIndustry(List<ServiceIndustryDto> serviceIndustryDtos);
    public ServiceIndustry modifyServiceIndustry();
    public void deleteServiceIndustry(long id);
}
