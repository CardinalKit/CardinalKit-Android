package edu.stanford.cardinalkit.domain.use_cases.tasklogs.data.repository

import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.domain.models.tasks.CKTaskLog
import edu.stanford.cardinalkit.domain.repositories.TaskLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTaskLogRepository: TaskLogRepository {
    private val taskLogs = mutableListOf<CKTaskLog>()

    override fun getTaskLogs(): Flow<Response<List<CKTaskLog>>> {
        return flow {
            emit(Response.Success(taskLogs))
        }
    }

    override suspend fun uploadTaskLog(log: CKTaskLog): Flow<Response<Void?>> = flow {
        taskLogs.add(log)
        emit(Response.Success(null))
    }

    fun insertTaskLog(log: CKTaskLog) {
        taskLogs.add(log)
    }
}