package com.gpcoder.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.gpcoder.repository", repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
public class JpaRepositoryConfiguration {

}
