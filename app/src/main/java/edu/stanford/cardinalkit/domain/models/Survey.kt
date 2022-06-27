package edu.stanford.cardinalkit.domain.models

import java.time.LocalDateTime
import java.util.*

data class Survey(
    var id: String? = null,
    var name: String? = null,
    var data: String? = null,
    var timestamp: LocalDateTime
)
