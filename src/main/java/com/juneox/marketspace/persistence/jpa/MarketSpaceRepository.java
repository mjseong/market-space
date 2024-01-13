package com.juneox.marketspace.persistence.jpa;

import com.juneox.marketspace.domain.model.meta.entity.MarketSpace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketSpaceRepository extends JpaRepository<MarketSpace, Long> {
}
