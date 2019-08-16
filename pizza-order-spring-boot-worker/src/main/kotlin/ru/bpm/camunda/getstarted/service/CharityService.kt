package ru.bpm.camunda.getstarted.service

import org.springframework.stereotype.Service

/**
 * @project: Camunda Education for Developers
 * @author: Maksim Davliatshin
 * @author: Artem Kuraev
 */

@Service("CharityService")
class CharityService {
    fun sendMoneytoCharity(): Boolean = true
}