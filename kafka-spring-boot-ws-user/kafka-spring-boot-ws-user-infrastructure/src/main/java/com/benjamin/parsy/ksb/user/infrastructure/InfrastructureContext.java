package com.benjamin.parsy.ksb.user.infrastructure;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackageClasses = InfrastructureContext.class)
@EnableJpaRepositories(basePackageClasses = InfrastructureContext.class)
@ComponentScan(basePackageClasses = InfrastructureContext.class)
public class InfrastructureContext {
}
