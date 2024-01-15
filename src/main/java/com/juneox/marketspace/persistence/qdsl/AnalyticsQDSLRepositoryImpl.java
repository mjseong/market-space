package com.juneox.marketspace.persistence.qdsl;

import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryAndCloseRateDto;
import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryAndStoreNumDto;
import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryDto;
import com.juneox.marketspace.domain.analysis.entity.QMarketSpaceAnalytics;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class AnalyticsQDSLRepositoryImpl implements AnalyticsQDSLRepository {
    private final JPAQueryFactory jpaQueryFactory;
//    private final SQLTemplates sqlTemplates;
//    @PersistenceContext
//    private final EntityManager entityManager;

    @Override
    public List<MSAnalyticsWithIndustryDto> findAllByYearQuarterCodes(List<String> yearAndQuarterCodes){
        QMarketSpaceAnalytics msa = QMarketSpaceAnalytics.marketSpaceAnalytics;

        return jpaQueryFactory
                .select(Projections.constructor(MSAnalyticsWithIndustryDto.class,
                        msa.yearAndQuarterCode, msa.serviceIndustry.serviceIndustryCodeName))
                .from(msa)
                .where(msa.yearAndQuarterCode.in(yearAndQuarterCodes))
                .groupBy(msa.yearAndQuarterCode, msa.serviceIndustry.serviceIndustryCodeName)
                .fetch();
    }

    @Override
    public List<MSAnalyticsWithIndustryAndStoreNumDto> findAllTopRankByYearQuarterCodesAndMsCode(List<String> yearAndQuarterCodes,
                                                                                                 List<String> marketSpaceCodes) {

        QMarketSpaceAnalytics msa = QMarketSpaceAnalytics.marketSpaceAnalytics;

        return jpaQueryFactory
                .select(Projections.constructor(MSAnalyticsWithIndustryAndStoreNumDto.class,
                        msa.yearAndQuarterCode,
                        msa.serviceIndustry.serviceIndustryCodeName,
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

        QMarketSpaceAnalytics msa = QMarketSpaceAnalytics.marketSpaceAnalytics;

        return jpaQueryFactory
                .select(Projections.constructor(MSAnalyticsWithIndustryAndCloseRateDto.class,
                        msa.yearAndQuarterCode,
                        msa.serviceIndustry.serviceIndustryCodeName,
                        msa.bizCloseStoreRate))
                .from(msa)
                .where(msa.yearAndQuarterCode.in(yearAndQuarterCodes)
                        .and(msa.marketSpace.marketSpaceCode.in(marketSpaceCodes)))
                .orderBy(msa.bizCloseStoreRate.asc())
                .fetch();
    }

    /**
     * TODO: QueryDSL을 쓸때 subQuery제대로 사용못하는 점에서 이 모듈의 장점을 더 찾아야 할것 같음.
     */
//    @Deprecated
//    public List<MSAnalyticsWithIndustryDto> findAllByYearAndQuarterCodesSQL(List<String> yearAndQuarterCodes){
//
//        QMarketSpaceAnalytics msa = QMarketSpaceAnalytics.marketSpaceAnalytics;
//        QServiceIndustry sii = QServiceIndustry.serviceIndustry;
//
//        StringPath subQueryAlias = Expressions.stringPath("sub_query_group");
//        NumberPath siId = Expressions.numberPath(Long.class, msa, "si_info_id");
//
//        JPASQLQuery<?> query = new JPASQLQuery(entityManager, sqlTemplates);
//
//        //TODO: subQuery내의 alias를 밖의 Query값으로 꺼내지 못해 컬럼을 찾지 못한다. 결국 subQuery하는 의미가 없음.
//        List<Tuple> result = query
//                .select(msa.yearAndQuarterCode, sii.serviceIndustryCodeName)
//                .from(
//                        JPAExpressions.selectDistinct(msa.yearAndQuarterCode, siId)
//                                .from(msa)
//                                .where(msa.yearAndQuarterCode.in(yearAndQuarterCodes)),subQueryAlias
//                )
//                .join(sii).on(siId.eq(sii.id))
//                .fetch();
//        return null;
//    }

}
