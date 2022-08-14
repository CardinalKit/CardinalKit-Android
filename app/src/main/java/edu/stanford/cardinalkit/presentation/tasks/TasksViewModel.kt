package edu.stanford.cardinalkit.presentation.tasks

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.stanford.cardinalkit.common.Constants
import edu.stanford.cardinalkit.common.toLocalDate
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.domain.models.tasks.CKTask
import edu.stanford.cardinalkit.domain.models.tasks.CKTaskLog
import edu.stanford.cardinalkit.domain.use_cases.tasklogs.TaskLogUseCases
import edu.stanford.cardinalkit.domain.use_cases.tasks.TasksUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class TasksViewModel @Inject constructor(
    @Named(Constants.TASKS_USE_CASES)
    private var tasksUseCases: TasksUseCases,
    @Named(Constants.TASKLOG_USE_CASES)
    private var taskLogUseCases: TaskLogUseCases
) : ViewModel() {
    var tasksState = mutableStateOf<Response<List<CKTask>>>(Response.Loading)
        private set

    var taskLogsState = mutableStateOf<Response<List<CKTaskLog>>>(Response.Loading)
        private set

    var totalTasksToday = mutableStateOf(0)
        private set

    var totalTasksCompleteToday = mutableStateOf(0)
        private set

    var currentDate = mutableStateOf<LocalDate>(LocalDate.now())
        private set

    var uploadTaskLogState = mutableStateOf<Response<Void?>>(Response.Loading)
        private set

    init {
        getTasks()
        getTaskLogs()
    }


    private fun getTasks() = viewModelScope.launch {
        tasksUseCases.getTasks().collect { response ->
            tasksState.value = response

            if (response is Response.Success) {
                response.data?.let { tasks ->
                    totalTasksToday.value = tasks.count {
                        it.schedule.isScheduledOn(LocalDate.now())
                    }
                }
            }
        }
    }

    private fun getTaskLogs() = viewModelScope.launch {
        taskLogUseCases.getTaskLogs().collect { response ->
            taskLogsState.value = response

            if (response is Response.Success) {
                response.data?.let { taskLogs ->
                    totalTasksCompleteToday.value = taskLogs.filter {
                        it.date.toLocalDate() == LocalDate.now()
                    }.distinctBy { it.taskID }.count()
                }
            }
        }
    }

    fun uploadTaskLog(log: CKTaskLog): Job = viewModelScope.launch {
        taskLogUseCases.uploadTaskLog(log).collect { response ->
            uploadTaskLogState.value = response
        }
    }

    fun setDate(date: LocalDate) {
        currentDate.value = date
    }

}
