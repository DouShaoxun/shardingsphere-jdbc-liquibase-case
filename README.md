# ShardingSphereDataSource+liquibase

- cn.cruder.dousx.sjlc.config.LiquibaseConfig
```java
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
```