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

@Component("FindSuppliersDelegate")
class FindSuppliersDelegate(
    private val log: Logger = LoggerFactory.getLogger(FindSuppliersDelegate::class.java)
) : JavaDelegate {
    companion object {
        private const val V_SUPPLIER_LIST = "supplierList"
    }

    override fun execute(execution: DelegateExecution) {
        val supplierList = mutableListOf("SUPPLIER_1", "SUPPLIER_2", "SUPPLIER_3")
        execution.setVariable(V_SUPPLIER_LIST, supplierList)

        log.info("FindSuppliersDelegate executed!")
        log.info("Variable {$V_SUPPLIER_LIST = $supplierList}")
    }
}