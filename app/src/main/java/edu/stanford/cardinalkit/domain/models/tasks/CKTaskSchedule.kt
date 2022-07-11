package edu.stanford.cardinalkit.domain.models.tasks

import java.util.*

data class CKTaskSchedule(
    val startDate: Date = Date(),
    val endDate: Date? = null,
    val description: String? = null
){
    fun isScheduledOn(date: Date): Boolean {
        return if (endDate != null) {
            this.startDate.compareTo(date) * date.compareTo(endDate) >= 0
        } else {
            date >= this.startDate
        }
    }
}
