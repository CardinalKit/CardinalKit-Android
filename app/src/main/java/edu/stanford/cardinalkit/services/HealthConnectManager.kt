package edu.stanford.cardinalkit.services

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.aggregate.AggregationResult
import androidx.health.connect.client.permission.Permission
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.AggregateRequest
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import java.time.Instant
import javax.inject.Inject

class HealthConnectManager @Inject constructor(
    private val context: Context
    ){
    val healthConnectClient by lazy { HealthConnectClient.getOrCreate(context) }

    var isAvailable = mutableStateOf(false); private set

    init {
        isAvailable.value = HealthConnectClient.isAvailable(context)
    }

    /**
     * Determines if all requested permissions are granted.
     */
    suspend fun hasAllPermissions(permissions: Set<Permission>): Boolean {
        return permissions == healthConnectClient.permissionController.getGrantedPermissions(permissions)
    }

    /**
     * Gets all step count records over a specified time range.
     */
    suspend fun readStepsByTimeRange(
        startTime: Instant,
        endTime: Instant
    ): List<StepsRecord>  {
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

}