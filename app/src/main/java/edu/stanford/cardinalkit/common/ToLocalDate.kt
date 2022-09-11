package edu.stanford.cardinalkit.common

import java.time.LocalDate
import java.time.ZoneId
import java.util.*

fun Date.toLocalDate(): LocalDate {
    return this.toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}
