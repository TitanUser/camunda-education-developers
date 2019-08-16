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

    @Scheduled(fixedDelay = SCHEDULE_DELAY)
    fun fetchExternalTask() {
        val tasks = externalTaskService
                .fetchAndLock(1, WORKER)
                .topic(WORKER_TOPIC, timeout + SCHEDULE_DELAY)
                .execute()

        tasks.forEach(::invokeMethod)
    }

    fun invokeMethod(task: LockedExternalTask) {
        try {
            val address: String = task.getVariables().getValue(V_ADDRESS, String::class.java)
                    ?: throw Exception("address must not be null")
            val deliveryBoyName = couriersDistributionService.distributeToCourier(address)

            if (deliveryBoyName.isNotEmpty()) {
                completeTask(task, mapOf(V_DELIVERYBOYNAME to deliveryBoyName))
                println("The External Task " + task.id + " has been completed!")
            }
        } catch (e: Exception) {
            handleFailure(task.id, e.message)
            println("The External Task " + task.id + " failed!")
        }
    }

    fun completeTask(task: LockedExternalTask, map: Map<String, Any>) =
            externalTaskService.complete(task.id, WORKER, map)

    fun handleFailure(id: String, stackTrace: String? = "") {
        externalTaskService.handleFailure(
                id,
                WORKER,
                "Invoke $WORKER has been failed",
                stackTrace,
                0,
                0
        )
    }

}