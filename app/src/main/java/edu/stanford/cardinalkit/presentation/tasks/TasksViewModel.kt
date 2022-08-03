package edu.stanford.cardinalkit.presentation.tasks

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.stanford.cardinalkit.common.Constants
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.domain.models.tasks.CKTask
import edu.stanford.cardinalkit.domain.models.tasks.CKTaskLog
import edu.stanford.cardinalkit.domain.use_cases.tasks.TasksUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class TasksViewModel @Inject constructor(
    @Named(Constants.TASKS_USE_CASES)
    private var useCases: TasksUseCases
): ViewModel() {
    private val _tasksState = mutableStateOf<Response<List<CKTask>>>(Response.Loading)
    val tasksState: State<Response<List<CKTask>>> = _tasksState

    private val _taskLogsState = mutableStateOf<Response<List<CKTaskLog>>>(Response.Loading)
    val taskLogsState: State<Response<List<CKTaskLog>>> = _taskLogsState

    private val _currentDate = mutableStateOf<LocalDate>(LocalDate.now())
    val currentDate: State<LocalDate> = _currentDate

    private val _uploadTaskLogState = mutableStateOf<Response<Void?>>(Response.Loading)
    val uploadTaskLogState: State<Response<Void?>> = _uploadTaskLogState

    init {
        // Sets up listeners for realtime updates from DB
        getTasks()
        getTaskLogs()
    }

    private fun getTasks() = viewModelScope.launch {
        useCases.getTasks().collect { response ->
            _tasksState.value = response
        }
    }

    private fun getTaskLogs() = viewModelScope.launch {
        useCases.getTaskLogs().collect { response ->
            _taskLogsState.value = response
        }
    }

    fun uploadTaskLog(log: CKTaskLog): Job = viewModelScope.launch {
        useCases.uploadTaskLog(log).collect { response ->
            _uploadTaskLogState.value = response
        }
    }

    fun setDate(date: LocalDate) {
        _currentDate.value = date
    }

}
