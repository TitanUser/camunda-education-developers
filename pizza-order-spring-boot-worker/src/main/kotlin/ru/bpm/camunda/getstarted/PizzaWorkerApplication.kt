package ru.bpm.camunda.getstarted

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * @project: Camunda Education for Developers
 * @author: Maksim Davliatshin
 * @author: Artem Kuraev
 */

@SpringBootApplication(scanBasePackages = arrayOf("ru.bpm"))
class PizzaWorkerApplication

fun main(args: Array<String>) {
    runApplication<PizzaWorkerApplication>(*args)
}
