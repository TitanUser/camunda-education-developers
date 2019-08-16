package ru.bpm.camunda.getstarted.delegate

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.DelegateTask
import org.camunda.bpm.engine.delegate.TaskListener
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import kotlin.random.Random

/**
 * @project: Camunda Education for Developers
 * @author: Maksim Davliatshin
 * @author: Artem Kuraev
 */

@Component("SelectSupliersDelegate")
class SelectSupliersDelegate(
    private val log: Logger = LoggerFactory.getLogger(SelectSupliersDelegate::class.java)
) : TaskListener {

    companion object {
        private const val V_LOCAL_IS_CHECKED_SUPPLIER = "isCheckedSupplier"
        private const val V_LOCAL_SUPPLIER = "supplier"
        private const val V_NEXT_STEP = "nextStep"
    }

    override fun notify(delegateTask: DelegateTask?) {
        log.info("SelectSupliersDelegate executed!")

        val randBool = Random.nextBoolean()
        log.info("Variable {$V_NEXT_STEP = $randBool}")

        if (delegateTask != null) {
            delegateTask.setVariable(V_NEXT_STEP, randBool)

            log.info("Variable {$V_LOCAL_IS_CHECKED_SUPPLIER = ${delegateTask.getVariable(V_LOCAL_IS_CHECKED_SUPPLIER)}}")
            log.info("Variable {$V_LOCAL_SUPPLIER = ${delegateTask.getVariable(V_LOCAL_SUPPLIER)}}")
        } else {
            log.info("!!! DelegateTask is null !!!")
        }
    }
}