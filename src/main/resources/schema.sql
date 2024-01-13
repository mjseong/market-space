CREATE TABLE if not exists market_space_group(
    ms_group_id bigint NOT NULL AUTO_INCREMENT,
    ms_group_code varchar(2) NOT NULL,
    ms_group_code_name varchar(4) NOT NULL,
    created_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at datetime(6) DEFAULT NULL,
    PRIMARY KEY(ms_group_id),
    UNIQUE KEY uidx_ms_groupcode (ms_group_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE if not exists market_space_info(
    ms_info_id bigint NOT NULL AUTO_INCREMENT,
    ms_code varchar(7) NOT NULL,
    ms_code_name varchar(100) NOT NULL,
    created_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at datetime(6) DEFAULT NULL,
    PRIMARY KEY(ms_info_id),
    UNIQUE KEY uidx_ms_code (ms_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE if not exists service_industry_info(
    si_info_id bigint NOT NULL AUTO_INCREMENT,
    si_code varchar(8) NOT NULL,
    si_code_name varchar(50) NOT NULL,
    created_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at datetime(6) DEFAULT NULL,
    PRIMARY KEY(si_info_id),
    UNIQUE KEY uidx_si_code (si_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE if not exists market_space_analytics(
    ms_analytics_id bigint NOT NULL AUTO_INCREMENT,
    yaer_quarter_code varchar(5) NOT NULL,
    ms_group_id bigint NOT NULL,
    ms_info_id bigint NOT NULL,
    si_info_id bigint NOT NULL,
    stores_number int NOT NULL DEFAULT 0,
    similar_industry_store_number int NOT NULL DEFAULT 0,
    biz_newstore_rate int NOT NULL DEFAULT 0,
    biz_newstore_number int NOT NULL DEFAULT 0,
    biz_closestore_rate int NOT NULL DEFAULT 0,
    biz_closestore_number int NOT NULL DEFAULT 0,
    franchise_store_number int NOT NULL DEFAULT 0,
    created_at datetime(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    updated_at datetime(6) DEFAULT NULL,
    PRIMARY KEY(ms_analytics_id),
    UNIQUE KEY uidx_msanaly_multi (yaer_quarter_code, ms_group_id, ms_info_id, si_info_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;