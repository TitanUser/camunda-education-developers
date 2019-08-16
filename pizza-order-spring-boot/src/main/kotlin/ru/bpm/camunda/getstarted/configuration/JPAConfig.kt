package ru.bpm.camunda.getstarted.configuration

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
 * @project: Camunda Education for Developers
 * @author: Maksim Davliatshin
 * @author: Artem Kuraev
 */

@Configuration
@EntityScan(basePackages = ["ru.bpm"], basePackageClasses = [Jsr310JpaConverters::class])
@EnableJpaRepositories(basePackages = ["ru.bpm"])
@EnableTransactionManagement
class JPAConfig
