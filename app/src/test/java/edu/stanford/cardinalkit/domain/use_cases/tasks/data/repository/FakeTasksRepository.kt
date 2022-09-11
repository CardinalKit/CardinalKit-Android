package edu.stanford.cardinalkit.domain.use_cases.tasks.data.repository

import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.domain.models.tasks.CKTask
import edu.stanford.cardinalkit.domain.repositories.TasksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTasksRepository : TasksRepository {
    private val tasks = mutableListOf<CKTask>()

    override fun getTasks(): Flow<Response<List<CKTask>>> {
        return flow {
            emit(Response.Success(tasks))
        }
    }

    fun insertTask(task: CKTask) {
        tasks.add(task)
    }
}
