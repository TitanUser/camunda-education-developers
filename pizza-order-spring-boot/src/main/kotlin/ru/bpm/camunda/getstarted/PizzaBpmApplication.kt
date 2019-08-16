package ru.bpm.camunda.getstarted

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InjectionPoint
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope
import org.springframework.scheduling.annotation.EnableScheduling

/**
 * @project: Camunda Education for Developers
 * @author: Maksim Davliatshin
 * @author: Artem Kuraev
 */

@SpringBootApplication(scanBasePackages = ["ru.bpm"])
@EnableScheduling
class PizzaBpmApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(PizzaBpmApplication::class.java, *args)
        }
    }

    @Bean
    @Scope("prototype")
    fun log(injectionPoint: InjectionPoint): Logger = LoggerFactory.getLogger(
            injectionPoint.methodParameter?.containingClass ?: injectionPoint.member.declaringClass
    )
}