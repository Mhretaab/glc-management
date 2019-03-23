package com.glc.managment.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "com.glc.managment")
class PersistenceJPAConfig {

    /**
    @Autowired
    DataSource dataSource

    @Bean
    public LocalContainerEntityManagerFactoryBean  entityManagerFactoryBean(){

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.POSTGRESQL);
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(getClass().getPackage().getName());
        factory.setDataSource(dataSource);

        return factory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(){

        JpaTransactionManager transactionManager = new JpaTransactionManager();

        transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject() );

        return transactionManager;
    }
     **/
}