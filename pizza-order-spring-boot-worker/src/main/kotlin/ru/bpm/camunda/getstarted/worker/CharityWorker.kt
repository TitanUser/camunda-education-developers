package ru.bpm.camunda.getstarted.worker

import org.camunda.bpm.client.ExternalTaskClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import ru.bpm.camunda.getstarted.model.CharityConfigProperties
import ru.bpm.camunda.getstarted.service.CharityService

/**
 * @project: Camunda Education for Developers
 * @author: Maksim Davliatshin
 * @author: Artem Kuraev
 */

@Component
class CharityWorker(
        @Qualifier("RBExternalTaskClientCharity")
        private val externalTaskClient: ExternalTaskClient,
        @Qualifier("CharityService")
        private val charityService: CharityService,
        @Qualifier("CharityProperties")
        private val charityProperties: CharityConfigProperties
) {
    private val log: Logger = LoggerFactory.getLogger(CharityWorker::class.java)

    init {
        //TODO: subscribe to the topic "charity_topic"
    }
}