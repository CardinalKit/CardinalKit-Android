package edu.stanford.cardinalkit.domain.use_cases.tasks.data.repository

import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.domain.models.tasks.CKTask
import edu.stanford.cardinalkit.domain.models.tasks.CKTaskLog
import edu.stanford.cardinalkit.domain.repositories.TasksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTasksRepository : TasksRepository {

    private val tasks = mutableListOf<CKTask>()
    private val taskLogs = mutableListOf<CKTaskLog>()

    override fun getTasks(): Flow<Response<List<CKTask>>> {
        return flow {
            emit(Response.Success(tasks))
        }
    }

    override fun getTaskLogs(): Flow<Response<List<CKTaskLog>>> {
        return flow {
            emit(Response.Success(taskLogs))
        }
    }

    override suspend fun uploadTaskLog(log: CKTaskLog): Flow<Response<Void?>> = flow {
        taskLogs.add(log)
        emit(Response.Success(null))
    }

    fun insertTask(task: CKTask) {
        tasks.add(task)
    }

    fun insertTaskLog(log: CKTaskLog) {
        taskLogs.add(log)
    }
}