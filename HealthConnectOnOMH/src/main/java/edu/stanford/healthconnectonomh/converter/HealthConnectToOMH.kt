package edu.stanford.healthconnectonomh.converter

import androidx.health.connect.client.records.Record
import androidx.health.connect.client.records.StepsRecord
import edu.stanford.healthconnectonomh.omhmodels.DataPoint
import edu.stanford.healthconnectonomh.omhmodels.Header
import edu.stanford.healthconnectonomh.omhmodels.StartEndTimeInterval
import edu.stanford.healthconnectonomh.omhmodels.StepCount
import edu.stanford.healthconnectonomh.omhmodels.StepCountUnit
import edu.stanford.healthconnectonomh.omhmodels.StepCountUnitValue
import edu.stanford.healthconnectonomh.omhmodels.TimeIntervalTimeFrame
import java.time.ZoneOffset
import java.util.UUID

fun Record.toOMHDataPoint(): DataPoint? {
    val schema = when(this) {
        is StepsRecord -> {
            StepCount(
                stepCount = StepCountUnitValue(unit = StepCountUnit.STEPS, value = this.count.toDouble()),
                effectiveTimeFrame = TimeIntervalTimeFrame(
                    StartEndTimeInterval(
                        this.startTime.atOffset(ZoneOffset.UTC),
                        this.endTime.atOffset(ZoneOffset.UTC)
                    )
                )
            )
        }
        else -> null
    }

    return schema?.let {
        val timeInterval = schema.effectiveTimeFrame as TimeIntervalTimeFrame
        DataPoint(
            header = Header(
                uuid = UUID.randomUUID(),
                schemaId = schema.schemaId,
                sourceCreationDateTime = (timeInterval.timeInterval as StartEndTimeInterval).startDateTime
            ),
            body = it
        )
    }
}