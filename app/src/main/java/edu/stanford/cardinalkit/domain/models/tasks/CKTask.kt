package edu.stanford.cardinalkit.domain.models.tasks

import java.time.LocalDate

data class CKTask(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val context: CKTaskContext = CKTaskContext(),
    val schedule: CKTaskSchedule? = null,
    var createdDate: LocalDate? = null,
    var updatedDate: LocalDate? = null,
    val isActive: Boolean = true
)
