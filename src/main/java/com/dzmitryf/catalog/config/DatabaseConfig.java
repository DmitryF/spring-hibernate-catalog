package com.dzmitryf.catalog.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Application database configuration
 */
@Configuration
@PropertySource({ "classpath:hibernate-config.properties" })
@ComponentScan({"com.dzmitryf.catalog.config",
                "com.dzmitryf.catalog.services",
                "com.dzmitryf.catalog.repositories"
                })
@EnableJpaRepositories(value = "com.dzmitryf.catalog.repositories",
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager")
@EnableTransactionManagement
public class DatabaseConfig {

    private static final String PACKAGE_MODEL = "com.dzmitryf.catalog.model";

    private static final String HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String HIBERNATE_BYTECODE_OPTIMIZER = "hibernate.bytecode.use_reflection_optimizer";
    private static final String SHOW_SQL = "show_sql";

    @Value("${hibernate.connection.driver_class}")
    private String dataSourceDriver;

    @Value("${hibernate.connection.url}")
    private String dataSourceUrl;

    @Value("${hibernate.connection.username}")
    private String dataSourceUsername;

    @Value("${hibernate.connection.password}")
    private String dataSourcePassword;

    @Value("${hibernate.dialect}")
    private String hibernateDialect;

    @Value("${hibernate.hbm2ddl.auto}")
    private String hibernateHbm2ddl;

    @Value("${hibernate.bytecode.use_reflection_optimizer}")
    private String hibrnateBytecodeOptimizer;

    @Value("${show_sql}")
    private String showSql;

    /**
     * Get {@link LocalContainerEntityManagerFactoryBean} for Jpa repositories
     * @return entity manager factory
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource());
        entityManager.setPackagesToScan(PACKAGE_MODEL);
        entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManager.setJpaProperties(getHibernateProperties());

        return entityManager;
    }

    /**
     * Get {@link JpaTransactionManager} for Jpa repositories
     * @return transaction manager
     */
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    /**
     * Get hibernate {@link Properties} for Jpa for entity manager
     * @return hibernate properties for entity manager
     */
    private Properties getHibernateProperties(){
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(HIBERNATE_DIALECT, hibernateDialect);
        hibernateProperties.setProperty(HIBERNATE_HBM2DDL_AUTO, hibernateHbm2ddl);
        hibernateProperties.setProperty(HIBERNATE_BYTECODE_OPTIMIZER, hibrnateBytecodeOptimizer);
        hibernateProperties.setProperty(SHOW_SQL, showSql);

        return hibernateProperties;
    }

    /**
     * Get {@link DataSource} for {@link LocalContainerEntityManagerFactoryBean}
     * @return data source for entity manager
     */
    @Bean
    public DataSource dataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(dataSourceDriver);
        dataSource.setUrl(dataSourceUrl);
        dataSource.setUsername(dataSourceUsername);
        dataSource.setPassword(dataSourcePassword);

        return dataSource;
    }
}
