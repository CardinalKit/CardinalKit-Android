package edu.stanford.cardinalkit.domain.models.tasks

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

data class CKTaskSchedule(
    val startDate: Date = Date(),
    val endDate: Date? = null,
    val description: String? = null
){
    fun isScheduledOn(date: LocalDate): Boolean {
        val startLocalDate = Instant.ofEpochMilli(startDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate()

        return if (endDate != null) {
            val endLocalDate = Instant.ofEpochMilli(endDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate()
            startLocalDate.compareTo(date) * date.compareTo(endLocalDate) >= 0
        } else {
            date >= startLocalDate
        }
    }
}
