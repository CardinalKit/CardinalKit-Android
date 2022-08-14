package edu.stanford.cardinalkit.domain.repositories

import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.domain.models.tasks.CKTask
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    fun getTasks(): Flow<Response<List<CKTask>>>
}