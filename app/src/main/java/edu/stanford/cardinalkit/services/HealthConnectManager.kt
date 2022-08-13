package edu.stanford.cardinalkit.services

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.health.connect.client.HealthConnectClient
import javax.inject.Inject

class HealthConnectManager @Inject constructor(
    private val context: Context
    ){
    private val healthConnectClient by lazy { HealthConnectClient.getOrCreate(context) }

    var isAvailable = mutableStateOf(false); private set

    init {
        isAvailable.value = HealthConnectClient.isAvailable(context)
    }
}