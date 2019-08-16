package ru.bpm.camunda.getstarted.model

/**
 * @project: Camunda Education for Developers
 * @author: Maksim Davliatshin
 * @author: Artem Kuraev
 */

data class CharityConfigProperties(
    var name: String? = null,
    var topics: Topic = Topic()
)

data class Topic(
    var charity: TopicData = TopicData()
)

data class TopicData (
   var name: String = "",
   var lockDuration: Long = 5000
)