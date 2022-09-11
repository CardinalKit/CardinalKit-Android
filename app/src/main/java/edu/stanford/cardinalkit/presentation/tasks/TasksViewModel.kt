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
import edu.stanford.cardinalkit.domain.usecases.tasklogs.TaskLogUseCases
import edu.stanford.cardinalkit.domain.usecases.tasks.TasksUseCases
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

    // Stores the date that is active on the tasks calendar
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
                    totalTasksToday.value = tasks.countTasksOnDate(LocalDate.now())
                }
            }
        }
    }

    private fun getTaskLogs() = viewModelScope.launch {
        taskLogUseCases.getTaskLogs().collect { response ->
            taskLogsState.value = response

            if (response is Response.Success) {
                response.data?.let { taskLogs ->
                    totalTasksCompleteToday.value = taskLogs.countTasksCompletedOnDate(LocalDate.now())
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

    // Extensions for task metrics

    fun List<CKTask>.countTasksOnDate(date: LocalDate): Int {
        return this.count {
            it.schedule.isScheduledOn(date)
        }
    }

    fun List<CKTaskLog>.countTasksCompletedOnDate(date: LocalDate): Int {
        return this.filter {
            it.date.toLocalDate() == date
        }.distinctBy { it.taskID }.count()
    }
}
