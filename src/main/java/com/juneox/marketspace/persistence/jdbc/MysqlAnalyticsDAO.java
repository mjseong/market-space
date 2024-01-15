package com.juneox.marketspace.persistence.jdbc;


import com.juneox.marketspace.domain.analysis.dto.MSAnalyticsWithIndustryDto;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class MysqlAnalyticsDAO implements AnalyticsDAO {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<MSAnalyticsWithIndustryDto> findAllByYearAndQuarterCodes(List<String> yearAndQuarterCodes) {
        HashMap param = new HashMap();
        param.put("yearAndQuarterCodes",yearAndQuarterCodes);

        RowMapper<MSAnalyticsWithIndustryDto> rowMapper = (rs, rowNum) ->
                new MSAnalyticsWithIndustryDto(
                        rs.getString("yaer_quarter_code"),
                        rs.getString("si_code_name")
                );

        String query = """
                    SELECT yaer_quarter_code, sii.si_code_name
                    FROM(
                        SELECT DISTINCT msa.yaer_quarter_code, msa.si_info_id
                        FROM market_space_analytics msa
                        WHERE msa.yaer_quarter_code IN (:yearAndQuarterCodes)                  
                    )a
                    INNER JOIN service_industry_info sii
                    ON a.si_info_id = sii.si_info_id
                    ORDER BY yaer_quarter_code;
                """;
        log.debug("parameter map: "+ param);
        log.debug("execute sql: \n" + query);
        return namedParameterJdbcTemplate.query(query, param,
                rowMapper);
    }
}
