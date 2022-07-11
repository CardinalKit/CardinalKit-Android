package edu.stanford.cardinalkit.domain.models

import java.util.*

data class User(
    val name: String = "",
    val userID: String = "",
    val email: String = "",
    val lastActive: Date = Date(),
    val createdDate: Date = Date()
)
