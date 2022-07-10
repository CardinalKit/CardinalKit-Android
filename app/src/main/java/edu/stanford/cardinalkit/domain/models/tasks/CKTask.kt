package edu.stanford.cardinalkit.domain.models.tasks

import java.util.*

data class CKTask(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val context: String = "",
    var createdDate: Date? = null,
    var updatedDate: Date? = null,
    val isActive: Boolean = true
)
