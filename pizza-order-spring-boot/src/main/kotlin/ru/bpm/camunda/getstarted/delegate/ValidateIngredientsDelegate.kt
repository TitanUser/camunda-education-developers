package ru.bpm.camunda.getstarted.delegate

import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import kotlin.random.Random

/**
 * @project: Camunda Education for Developers
 * @author: Maksim Davliatshin
 * @author: Artem Kuraev
 */

@Component("ValidateIngredientsDelegate")
class ValidateIngredientsDelegate(
    private val log: Logger = LoggerFactory.getLogger(ValidateIngredientsDelegate::class.java)
) : JavaDelegate {
    companion object {
        private const val V_ALL_INGREDIENTS_EXISTS = "AllIngredientsExists"
    }

    override fun execute(execution: DelegateExecution) {
        val randBool = Random.nextBoolean()
        execution.setVariable(V_ALL_INGREDIENTS_EXISTS, randBool)

        log.info("ValidateIngredientsDelegate executed!")
        log.info("Variable {$V_ALL_INGREDIENTS_EXISTS = $randBool}")
    }
}