package edu.stanford.cardinalkit.domain.models.tasks

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import java.util.*

class CKTaskScheduleTest {

    private lateinit var schedule: CKTaskSchedule

    @Before
    fun setUp(){
        schedule = CKTaskSchedule(startDate = Date(), endDate = null, description = "test schedule")
    }

    @Test
    fun isScheduledOnTest() {
        assertThat(schedule.isScheduledOn(Date())).isTrue()
    }
}