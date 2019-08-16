package ru.bpm.camunda.getstarted.configuration

import org.camunda.bpm.engine.IdentityService
import org.camunda.bpm.spring.boot.starter.property.CamundaBpmProperties
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

/**
 * @project: Camunda Education for Developers
 * @author: Maksim Davliatshin
 * @author: Artem Kuraev
 */

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class SecurityConfig(
    private val authProvider: CamundaAuthenticationProvider,
    private val identityService: IdentityService
) : WebSecurityConfigurerAdapter() {
    companion object {
        const val ATTR_SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT"
    }

    override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .antMatchers(
                "/actuator/**",
                "/api/**",
                "/lib/**",
                "/webjars/**",
                "/resources/**",
                "/error/**",
                "/favicon.ico",
                "/app/**",
                "/"
            ).permitAll()
            .anyRequest().authenticated()
            .and().httpBasic()
            .and().csrf().disable()
//            .addFilterAfter(camundaUserSessionFilter(), BasicAuthenticationFilter::class.java)
    }

// For Spring auth
//    @Throws(Exception::class)
//    override fun configure(auth: AuthenticationManagerBuilder) {
//        auth.authenticationProvider(authProvider)
//    }
//
//    @Bean
//    fun camundaUserSessionFilter(): Filter {
//        return object : GenericFilterBean() {
//
//            @Throws(IOException::class, ServletException::class)
//            override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
//                val servletRequest = request as HttpServletRequest
//                val sci = servletRequest.getSession(false)?.getAttribute(ATTR_SPRING_SECURITY_CONTEXT) as SecurityContextImpl?
//
//                if (sci != null) {
//                    val login = sci.authentication.principal as String
//                    val groups = sci.authentication.authorities.map { it.authority }
//                    identityService.setAuthentication(login, groups)
//                }
//                chain.doFilter(request, response)
//            }
//
//        }
//    }

    @Bean
    fun camundaBpmWebappReInitializer(properties: CamundaBpmProperties): CamundaBpmWebappReInitializer =
        CamundaBpmWebappReInitializer(properties)
}
