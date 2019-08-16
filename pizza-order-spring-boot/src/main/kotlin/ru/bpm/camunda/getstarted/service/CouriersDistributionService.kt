package ru.bpm.camunda.getstarted.service

import org.springframework.stereotype.Service

/**
 * @project: Camunda Education for Developers
 * @author: Maksim Davliatshin
 * @author: Artem Kuraev
 */

@Service
class CouriersDistributionService {
    fun distributeToCourier(address: String): String =
            when (address) {
                "неизвестность" -> throw Exception("Unknown delivery address")
                "зависни" -> ""
                "супер адрес" -> "superCourier"
                else -> "deliveryBoy"
            }
}