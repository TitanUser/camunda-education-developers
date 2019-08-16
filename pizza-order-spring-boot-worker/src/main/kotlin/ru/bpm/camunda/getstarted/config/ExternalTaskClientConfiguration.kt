package ru.bpm.camunda.getstarted.config

import org.camunda.bpm.client.ExternalTaskClient
import org.camunda.bpm.client.interceptor.ClientRequestContext
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.bpm.camunda.getstarted.model.CharityConfigProperties

/**
 * @project: Camunda Education for Developers
 * @author: Maksim Davliatshin
 * @author: Artem Kuraev
 */

@Configuration
class ExternalTaskClientConfiguration(
    @Value("\${bpm.credentials.authToken}")
    private val authToken: String,
    @Value("\${bpm.host}")
    private val host: String
) {

    @Bean
    @Qualifier("CharityProperties")
    @ConfigurationProperties(prefix = "bpm.workers.charity")
    fun charityProperties() = CharityConfigProperties()

    @Bean
    @Qualifier("RBExternalTaskClientCharity")
    fun externalTaskClientCharity(): ExternalTaskClient = ExternalTaskClient.create()
        .baseUrl(host)
        .workerId(charityProperties().name)
        .addInterceptor { requestContext: ClientRequestContext ->
            requestContext.addHeader("Authorization", authToken)
        }
        .build()
}