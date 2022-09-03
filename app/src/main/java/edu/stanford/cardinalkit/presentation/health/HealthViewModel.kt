package edu.stanford.cardinalkit.presentation.health

import androidx.compose.runtime.mutableStateOf
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.StepsRecord
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.stanford.cardinalkit.services.HealthConnectManager
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

@HiltViewModel
class HealthViewModel @Inject constructor(
    var healthConnectManager: HealthConnectManager
) : ViewModel() {
    var totalStepsToday = mutableStateOf<Long>(0)
        private set

    private var permissionsGranted = mutableStateOf<Boolean>(false)

    val permissions = setOf(
        HealthPermission.createReadPermission(StepsRecord::class)
    )

    init {
        viewModelScope.launch {
            permissionsGranted.value = healthConnectManager.hasAllPermissions(permissions)
            getTotalStepsToday()
        }
    }

    fun getTotalStepsToday() = viewModelScope.launch {
        if (permissionsGranted.value) {
            totalStepsToday.value = healthConnectManager.aggregateSteps(
                startTime = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant(),
                endTime = Instant.now()
            )
        }
    }

    fun updatePermissionsStatus(granted: Boolean) {
        permissionsGranted.value = granted
    }
}