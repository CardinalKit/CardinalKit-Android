package edu.stanford.cardinalkit.domain.repositories

import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.domain.models.tasks.CKTask
import edu.stanford.cardinalkit.domain.models.tasks.CKTaskLog
import kotlinx.coroutines.flow.Flow
import java.util.*

interface TasksRepository {
    fun getTasks(): Flow<Response<List<CKTask>>>
    fun getTaskLogs(): Flow<Response<List<CKTaskLog>>>
    suspend fun uploadTaskLog(log: CKTaskLog): Flow<Response<Void?>>
}