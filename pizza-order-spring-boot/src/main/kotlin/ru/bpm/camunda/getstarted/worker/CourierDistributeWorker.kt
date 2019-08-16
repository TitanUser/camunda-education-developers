package ru.bpm.camunda.getstarted.worker

import org.camunda.bpm.engine.ExternalTaskService
import org.camunda.bpm.engine.externaltask.LockedExternalTask
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.bpm.camunda.getstarted.service.CouriersDistributionService

/**
 * @project: Camunda Education for Developers
 * @author: Maksim Davliatshin
 * @author: Artem Kuraev
 */

@Component
class CourierDistributeWorker(
        private val externalTaskService: ExternalTaskService,
        private val couriersDistributionService: CouriersDistributionService,
        private val log: Logger = LoggerFactory.getLogger(CourierDistributeWorker::class.java)
) {
    companion object {
        const val V_ADDRESS = "address"
        const val V_DELIVERYBOYNAME = "deliveryBoyName"

        var timeout: Long = 120000
        const val SCHEDULE_DELAY: Long = 5 * 1000
        const val WORKER = "courierWorker"
        private const val WORKER_TOPIC = "couriers_distribution_topic"
    }
    //TODO: subscribe to the topic "couriers_distribution_topic"
}