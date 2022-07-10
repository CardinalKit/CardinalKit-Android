package edu.stanford.cardinalkit.domain.models.tasks

import java.util.*

data class CKTaskSchedule(
    val startDate: Date = Date(),
    val endDate: Date? = null,
    val interval: Int = 0,
    val description: String? = null
)
