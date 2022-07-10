package edu.stanford.cardinalkit.domain.models.tasks

import java.time.LocalDate

data class CKTaskSchedule(
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate? = null,
    val interval: Int = 0,
    val description: String? = null
)
