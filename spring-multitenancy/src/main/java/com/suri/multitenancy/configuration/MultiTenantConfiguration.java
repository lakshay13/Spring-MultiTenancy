package com.suri.multitenancy.configuration;

import com.suri.multitenancy.components.MultiTenantFilter;
import com.suri.multitenancy.utils.TenantConstants;
import org.hibernate.MultiTenancyStrategy;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.servlet.Filter;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MultiTenantConfiguration {

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, MultiTenantConnectionProvider multiTenantConnectionProviderImpl,
                                                                       CurrentTenantIdentifierResolver currentTenantIdentifierResolverImpl) {
        Map<String, Object> properties = new HashMap<>();
        properties.putAll(jpaProperties.getHibernateProperties(dataSource));
        properties.put(Environment.MULTI_TENANT, MultiTenancyStrategy.SCHEMA);
        properties.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProviderImpl);
        properties.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolverImpl);

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPersistenceUnitName(TenantConstants.TENANT_KEY);
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.suri");
        em.setJpaVendorAdapter(jpaVendorAdapter());
        em.setJpaPropertyMap(properties);

        // to read and construct database
        org.hibernate.cfg.Configuration config = new org.hibernate.cfg.Configuration().configure("/hibernate.cfg.xml");
        SessionFactory sessionFactory = config.buildSessionFactory();
        return em;
    }

    @Bean
    public FilterRegistrationBean myFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        Filter tenantFilter = new MultiTenantFilter();
        beanFactory.autowireBean(tenantFilter);
        registration.setFilter(tenantFilter);
        registration.addUrlPatterns("/*");
        return registration;
    }
}
