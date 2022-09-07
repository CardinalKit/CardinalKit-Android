package edu.stanford.cardinalkit.services

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.aggregate.AggregationResult
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.WeightRecord
import androidx.health.connect.client.request.AggregateRequest
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import androidx.health.connect.client.units.Mass
import java.time.Instant
import javax.inject.Inject

class HealthConnectManager @Inject constructor(
    private val context: Context
) {
    val healthConnectClient by lazy { HealthConnectClient.getOrCreate(context) }

    var isAvailable = mutableStateOf(false); private set

    init {
        isAvailable.value = HealthConnectClient.isAvailable(context)
    }

    /**
     * Determines if all requested permissions are granted.
     */
    suspend fun hasAllPermissions(permissions: Set<HealthPermission>): Boolean {
        return permissions == healthConnectClient.permissionController.getGrantedPermissions(
            permissions
        )
    }

    /**
     * Gets all step count records over a specified time range.
     */
    suspend fun readStepsByTimeRange(
        startTime: Instant,
        endTime: Instant
    ): List<StepsRecord> {
        val response =
            healthConnectClient.readRecords(
                ReadRecordsRequest(
                    StepsRecord::class,
                    timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
                )
            )
        return response.records
    }

    /**
     * Aggregates step records for a particular time range.
     */
    suspend fun aggregateSteps(
        startTime: Instant,
        endTime: Instant
    ): Long {
        val response =
            healthConnectClient.aggregate(
                AggregateRequest(
                    metrics = setOf(StepsRecord.COUNT_TOTAL),
                    timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
                )
            )
        return response[StepsRecord.COUNT_TOTAL] ?: 0
    }

    /**
     * Computes the minimum and maximum heart rate over a time range.
     */
    suspend fun aggregateHeartRate(
        startTime: Instant,
        endTime: Instant
    ): AggregationResult {
        val response =
            healthConnectClient.aggregate(
                AggregateRequest(
                    setOf(HeartRateRecord.BPM_MAX, HeartRateRecord.BPM_MIN),
                    timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
                )
            )
        return response
    }

    /**
     * Computes the average weight for a time period
     */
    suspend fun getAverageWeight(startTime: Instant, endTime: Instant): Mass? {
        val response =
            healthConnectClient.aggregate(
                AggregateRequest(
                    metrics = setOf(WeightRecord.WEIGHT_AVG),
                    timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
                )
            )
        return response[WeightRecord.WEIGHT_AVG]
    }

    /**
     * Get all weight records for a certain time period
     */
    suspend fun getWeightRecords(startTime: Instant, endTime: Instant): List<WeightRecord> {
        val response =
            healthConnectClient.readRecords(
                ReadRecordsRequest(
                    recordType = WeightRecord::class,
                    timeRangeFilter = TimeRangeFilter.between(startTime, endTime)
                )
            )
        return response.records
    }
}