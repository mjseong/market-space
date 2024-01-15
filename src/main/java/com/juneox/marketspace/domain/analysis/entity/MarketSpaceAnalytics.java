package com.juneox.marketspace.domain.analysis.entity;

import com.juneox.marketspace.domain.meta.entity.MarketSpace;
import com.juneox.marketspace.domain.meta.entity.MarketSpaceGroup;
import com.juneox.marketspace.domain.meta.entity.ServiceIndustry;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "market_space_analytics")
@Entity
public class MarketSpaceAnalytics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ms_analytics_id")
    Long id;

    @Column(name = "yaer_quarter_code")
    String yearAndQuarterCode;

    @Column(name = "stores_number")
    int storesNumber;

    @Column(name = "similar_industry_store_number")
    int similarIndustryStoreNumber;

    @Column(name = "biz_newstore_rate")
    int bizNewStoreRate;

    @Column(name = "biz_newstore_number")
    int bizNewStoreNumber;

    @Column(name = "biz_closestore_rate")
    int bizCloseStoreRate;

    @Column(name = "biz_closestore_number")
    int bizCloseStoreNumber;

    @Column(name = "franchise_store_number")
    int franchiseStoreNumber;

    @ManyToOne(targetEntity = MarketSpaceGroup.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ms_group_id", referencedColumnName = "ms_group_id")
    MarketSpaceGroup marketSpaceGroup;

    @ManyToOne(targetEntity = MarketSpace.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ms_info_id", referencedColumnName = "ms_info_id")
    MarketSpace marketSpace;

    @ManyToOne(targetEntity = ServiceIndustry.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "si_info_id", referencedColumnName = "si_info_id")
    ServiceIndustry serviceIndustry;

}
