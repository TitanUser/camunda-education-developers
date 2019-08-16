package ru.bpm.camunda.getstarted.configuration

import com.mchange.v2.c3p0.ComboPooledDataSource
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @project: Camunda Education for Developers
 * @author: Maksim Davliatshin
 * @author: Artem Kuraev
 */

@Configuration
@EnableProcessApplication
class CamundaConfig {
    @Bean("camundaBpmDataSource")
    @ConfigurationProperties(prefix = "camunda.datasource")
    fun camundaDataSource(): ComboPooledDataSource = ComboPooledDataSource()
}