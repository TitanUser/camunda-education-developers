package ru.bpm.camunda.getstarted.configuration

import org.camunda.bpm.engine.IdentityService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

/**
 * @project: Camunda Education for Developers
 * @author: Maksim Davliatshin
 * @author: Artem Kuraev
 */

@Component
class CamundaAuthenticationProvider(
    private val identityService: IdentityService
) : AuthenticationProvider {
    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication? {

        val name = authentication.name
        val password = authentication.credentials.toString()
        // use the credentials
        // and authenticate against the third-party system
        return if (identityService.checkPassword(name, password)) {
            val groups = identityService.createGroupQuery().groupMember(name).list().map { it.id }
            identityService.setAuthentication(name, groups)
            UsernamePasswordAuthenticationToken(
                name, password, groups.map { SimpleGrantedAuthority(it) }
            )
        } else {
            null
        }
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == UsernamePasswordAuthenticationToken::class.java
    }
}