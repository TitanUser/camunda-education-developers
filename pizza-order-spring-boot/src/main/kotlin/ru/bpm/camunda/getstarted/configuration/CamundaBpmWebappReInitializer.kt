package ru.bpm.camunda.getstarted.configuration

import org.camunda.bpm.spring.boot.starter.property.CamundaBpmProperties
import org.camunda.bpm.spring.boot.starter.webapp.filter.LazySecurityFilter
import org.camunda.bpm.webapp.impl.security.auth.AuthenticationFilter
import org.springframework.boot.web.servlet.ServletContextInitializer
import java.util.*
import java.util.Collections.singletonMap
import javax.servlet.DispatcherType
import javax.servlet.Filter
import javax.servlet.FilterRegistration
import javax.servlet.ServletContext


/**
 * @project: Camunda Education for Developers
 * @author: Maksim Davliatshin
 * @author: Artem Kuraev
 */

class CamundaBpmWebappReInitializer(private val properties: CamundaBpmProperties) : ServletContextInitializer {
    private var servletContext: ServletContext? = null

    override fun onStartup(servletContext: ServletContext) {
        this.servletContext = servletContext
        registerFilter(
            "Authentication Filter",
            AuthenticationFilter::class.java,
            null,
            "/api/*",
            "/app/*",
            "/auth/*",
            "/view/*",
            "/login/*"
        )

        registerFilter(
            "Security Filter",
            LazySecurityFilter::class.java,
            singletonMap<String, String>("configFile", properties.webapp.securityConfigFile),
            "/api/*",
            "/app/*",
            "/auth/*",
            "/view/*",
            "/login/*"
        )
    }

    private fun registerFilter(
        filterName: String, filterClass: Class<out Filter>, initParameters: Map<String, String>?,
        vararg urlPatterns: String
    ): FilterRegistration? {
        var filterRegistration: FilterRegistration? = servletContext?.getFilterRegistration(filterName)

        if (filterRegistration == null) {
            filterRegistration = servletContext?.addFilter(filterName, filterClass)
            if (filterRegistration != null) {
                filterRegistration.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, *urlPatterns)

                if (initParameters != null) {
                    filterRegistration.initParameters = initParameters
                }
            }
        }
        return filterRegistration
    }

}