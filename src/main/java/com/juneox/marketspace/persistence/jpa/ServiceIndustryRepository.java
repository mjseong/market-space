package com.juneox.marketspace.persistence.jpa;

import com.juneox.marketspace.domain.model.meta.entity.ServiceIndustry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceIndustryRepository extends JpaRepository<ServiceIndustry, Long> {
}
