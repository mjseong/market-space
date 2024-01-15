package com.juneox.marketspace.persistence.jpa;

import com.juneox.marketspace.domain.meta.entity.MarketSpaceGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketSpaceGroupRepository extends JpaRepository<MarketSpaceGroup, Long> {
}
