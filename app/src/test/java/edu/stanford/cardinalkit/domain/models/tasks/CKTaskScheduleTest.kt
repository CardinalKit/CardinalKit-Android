package edu.stanford.cardinalkit.domain.models.tasks

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.util.*

class CKTaskScheduleTest {

    private lateinit var schedule: CKTaskSchedule

    @Before
    fun setUp(){
        schedule = CKTaskSchedule(startDate = Date(), endDate = null, interval = 3, description = "test schedule")
    }

    @Test
    fun isScheduledOnTest() {
        val today = LocalDate.now()
        assertThat(schedule.isScheduledOn(today.plusDays(3))).isTrue()
        assertThat(schedule.isScheduledOn(today.plusDays(4))).isFalse()
    }
}