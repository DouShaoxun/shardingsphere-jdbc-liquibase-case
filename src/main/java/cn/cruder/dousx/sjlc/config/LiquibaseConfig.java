package cn.cruder.dousx.sjlc.config;

import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class LiquibaseConfig {

    @Bean("liquibaseDataSourceProperties")
    @ConfigurationProperties("msjc.liquibase.datasource")
    DataSourceProperties liquibaseDataSourceProperties() {
        return new DataSourceProperties();
    }

    // success HikariDataSource+liquibase
    //@Bean
    //@ConditionalOnBean(name = "liquibaseDataSourceProperties")
    //public SpringLiquibase liquibase(DataSourceProperties liquibaseDataSourceProperties) {
    //    SpringLiquibase liquibase = new SpringLiquibase();
    //    DataSource dataSource = liquibaseDataSourceProperties.initializeDataSourceBuilder().build();
    //    log.info("liquibase dataSource {}", dataSource.getClass());
    //    liquibase.setDataSource(dataSource);
    //    liquibase.setChangeLog("classpath:db/liquibase/changelog.xml");
    //    liquibase.setResourceLoader(new DefaultResourceLoader());
    //    return liquibase;
    //}

     // fail ShardingSphereDataSource+liquibase
    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        log.info("liquibase dataSource {}", dataSource.getClass());
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:db/liquibase/changelog.xml");
        liquibase.setResourceLoader(new DefaultResourceLoader());
        return liquibase;
    }
}
