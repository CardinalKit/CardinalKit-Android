package edu.stanford.cardinalkit.domain.models.tasks

import java.util.*

data class CKTask(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val description: String = "",
    val context: CKTaskContext = CKTaskContext(),
    val schedule: CKTaskSchedule = CKTaskSchedule(),
    var createdDate: Date? = null,
    var updatedDate: Date? = null,
    val isActive: Boolean = true
)
