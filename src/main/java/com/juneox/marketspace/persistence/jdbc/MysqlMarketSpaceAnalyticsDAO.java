package com.juneox.marketspace.persistence.jdbc;


import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryAndCloseRateDto;
import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryDto;
import com.juneox.marketspace.domain.analysis.entity.MarketSpaceAnalytics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Repository
public class MysqlMarketSpaceAnalyticsDAO implements MarketSpaceAnalyticsDAO {


    private final int batchSize;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public MysqlMarketSpaceAnalyticsDAO(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                        @Value("${app.market-space.batch-size}")int batchSize){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.batchSize = batchSize;
    }

    @Transactional(readOnly = true)
    @Override
    public List<MSAnalyticsWithIndustryDto> findAllByYearAndQuarterCodes(List<String> yearAndQuarterCodes) {
        HashMap param = new HashMap();
        param.put("yearAndQuarterCodes",yearAndQuarterCodes);

        RowMapper<MSAnalyticsWithIndustryDto> rowMapper = (rs, rowNum) ->
                new MSAnalyticsWithIndustryDto(
                        rs.getString("si_code_name")
                );

        String query = """
                    SELECT sii.si_code_name
                    FROM(
                        SELECT DISTINCT msa.si_info_id
                        FROM market_space_analytics msa
                        WHERE msa.yaer_quarter_code IN (:yearAndQuarterCodes)                  
                    )a
                    INNER JOIN service_industry_info sii
                    ON a.si_info_id = sii.si_info_id;
                """;
        log.debug("parameter map: "+ param);
        log.debug("execute sql: \n" + query);
        return namedParameterJdbcTemplate.query(query, param,
                rowMapper);
    }

    @Transactional(readOnly = true)
    @Override
    public List<MSAnalyticsWithIndustryAndCloseRateDto> findAllCloseRateByYearQuarterCodesAndMsCode(List<String> yearAndQuarterCodes, List<String> marketSpaceCodes) {
        HashMap param = new HashMap();
        param.put("yearAndQuarterCodes",yearAndQuarterCodes);
        param.put("marketSpaceCodes",marketSpaceCodes);

        RowMapper<MSAnalyticsWithIndustryDto> rowMapper = (rs, rowNum) ->
                new MSAnalyticsWithIndustryAndCloseRateDto(
                        rs.getString("si_code_name"),
                        rs.getString("yaer_quarter_code"),
                        rs.getString("ms_code"),
                        rs.getInt("biz_closestore_rate")
                );

        String query = """
                    SELECT a.yaer_quarter_code, a.ms_code, sii.si_code_name, a.biz_closestore_rate
                    FROM
                    (
                    SELECT yaer_quarter_code, msi.ms_code, msi.ms_info_id, msa.si_info_id, biz_closestore_rate,
                    DENSE_RANK() over (PARTITION BY ms_info_id ORDER BY biz_closestore_rate) AS rate_rank
                    FROM market_space_analytics msa
                    INNER JOIN market_space_info msi
                        ON msa.ms_info_id = msi.ms_info_id
                        AND msa.yaer_quarter_code in (:yearAndQuarterCodes)
                        AND msi.ms_code in (:marketSpaceCodes)
                    )a
                    INNER JOIN service_industry_info sii
                    ON a.si_info_id = sii.si_info_id
                    AND rate_rank = 1;
                """;
        log.debug("parameter map: "+ param);
        log.debug("execute sql: \n" + query);
        return namedParameterJdbcTemplate.query(query, param,
                rowMapper);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveAll(List<MarketSpaceAnalytics> marketSpaceAnalyticsList){

        JdbcTemplate jdbcTemplate = namedParameterJdbcTemplate.getJdbcTemplate();

        if(jdbcTemplate != null){
            String query = """
                INSERT IGNORE INTO marketspace.market_space_analytics
                (yaer_quarter_code, ms_group_id, ms_info_id, si_info_id, stores_number, similar_industry_store_number, biz_newstore_rate, biz_newstore_number, biz_closestore_rate, biz_closestore_number, franchise_store_number)
                VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);                                
                """;

            jdbcTemplate.batchUpdate(query, marketSpaceAnalyticsList, batchSize,
                            (PreparedStatement ps, MarketSpaceAnalytics marketSpaceAnalytics) -> {
                                ps.setString(1, marketSpaceAnalytics.getYearAndQuarterCode());
                                ps.setLong(2, marketSpaceAnalytics.getMarketSpaceGroup().getId());
                                ps.setLong(3, marketSpaceAnalytics.getMarketSpace().getId());
                                ps.setLong(4, marketSpaceAnalytics.getServiceIndustry().getId());
                                ps.setLong(5, marketSpaceAnalytics.getStoresNumber());
                                ps.setLong(6, marketSpaceAnalytics.getSimilarIndustryStoreNumber());
                                ps.setLong(7, marketSpaceAnalytics.getBizNewStoreRate());
                                ps.setLong(8, marketSpaceAnalytics.getBizNewStoreNumber());
                                ps.setLong(9, marketSpaceAnalytics.getBizCloseStoreRate());
                                ps.setLong(10, marketSpaceAnalytics.getBizCloseStoreNumber());
                                ps.setLong(11, marketSpaceAnalytics.getFranchiseStoreNumber());
                            });
        }
    }
}
