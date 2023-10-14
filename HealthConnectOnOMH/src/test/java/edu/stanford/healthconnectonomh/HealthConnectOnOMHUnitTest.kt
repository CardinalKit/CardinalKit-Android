package edu.stanford.healthconnectonomh

import androidx.health.connect.client.records.StepsRecord
import edu.stanford.healthconnectonomh.converter.toOMHDataPoint
import edu.stanford.healthconnectonomh.omhmodels.StartEndTimeInterval
import edu.stanford.healthconnectonomh.omhmodels.StepCount
import edu.stanford.healthconnectonomh.omhmodels.StepCountUnit
import org.junit.Assert
import org.junit.Test
import edu.stanford.healthconnectonomh.converter.toOMHDataPoint
import edu.stanford.healthconnectonomh.omhmodels.DataPoint
import edu.stanford.healthconnectonomh.omhmodels.TimeIntervalTimeFrame

import org.junit.Assert.*
import java.time.Instant
import java.time.ZoneOffset

class HealthConnectOnOMHUnitTest {
    @Test
    fun `test toOMHDataPoint with StepsRecord`() {
        // Arrange
        val startTime = Instant.parse("2023-10-10T10:00:00.00Z")
        val endTime = Instant.parse("2023-10-10T11:00:00.00Z")
        val stepCount: Long = 1000
        val stepsRecord = StepsRecord(
            startTime,
            startZoneOffset = ZoneOffset.UTC,
            endTime = endTime,
            endZoneOffset = ZoneOffset.UTC,
            count = stepCount
        )

        // Act
        val result = stepsRecord.toOMHDataPoint()

        // Assert
        Assert.assertTrue(result is DataPoint)
        val dataPoint = result?.body as StepCount
        Assert.assertEquals(StepCountUnit.STEPS, dataPoint.stepCount.unit)
        Assert.assertEquals(stepCount.toDouble(), dataPoint.stepCount.value, 0.0)
    }
}