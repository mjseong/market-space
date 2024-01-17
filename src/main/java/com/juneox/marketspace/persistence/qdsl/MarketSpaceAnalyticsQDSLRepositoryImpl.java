package com.juneox.marketspace.persistence.qdsl;

import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryAndCloseRateDto;
import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryAndStoreNumDto;
import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryDto;
import com.juneox.marketspace.domain.analysis.entity.QMarketSpaceAnalytics;
import com.juneox.marketspace.domain.exception.NoSupportedException;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class MarketSpaceAnalyticsQDSLRepositoryImpl implements MarketSpaceAnalyticsQDSLRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MSAnalyticsWithIndustryDto> findAllByYearQuarterCodes(List<String> yearAndQuarterCodes){
        throw new NoSupportedException();
    }

    @Override
    public List<MSAnalyticsWithIndustryAndStoreNumDto> findAllTopRankByYearQuarterCodesAndMsCode(List<String> yearAndQuarterCodes,
                                                                                                 List<String> marketSpaceCodes) {

        QMarketSpaceAnalytics msa = QMarketSpaceAnalytics.marketSpaceAnalytics;

        return jpaQueryFactory
                .select(Projections.constructor(MSAnalyticsWithIndustryAndStoreNumDto.class,
                        msa.serviceIndustry.serviceIndustryCodeName,
                        msa.yearAndQuarterCode,
                        msa.storesNumber))
                .from(msa)
                .where(msa.yearAndQuarterCode.in(yearAndQuarterCodes)
                        .and(msa.marketSpace.marketSpaceCode.in(marketSpaceCodes)))
                .orderBy(msa.storesNumber.desc())
                .limit(5)
                .fetch();
    }

    @Override
    public List<MSAnalyticsWithIndustryAndCloseRateDto> findAllCloseRateByYearQuarterCodesAndMsCode(List<String> yearAndQuarterCodes,
                                                                                                       List<String> marketSpaceCodes) {
        throw new NoSupportedException();
    }

}
