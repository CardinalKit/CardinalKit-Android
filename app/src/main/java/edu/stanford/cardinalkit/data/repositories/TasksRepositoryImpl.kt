package edu.stanford.cardinalkit.data.repositories

import com.google.firebase.firestore.CollectionReference
import edu.stanford.cardinalkit.common.Constants
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.domain.models.SurveyResult
import edu.stanford.cardinalkit.domain.models.tasks.CKTask
import edu.stanford.cardinalkit.domain.models.tasks.CKTaskLog
import edu.stanford.cardinalkit.domain.repositories.TasksRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class TasksRepositoryImpl @Inject constructor(
    @Named(Constants.TASKS_REF)
    private val tasksRef: CollectionReference?,
    @Named(Constants.TASKLOG_REF)
    private val taskLogRef: CollectionReference?
) : TasksRepository {
    override fun getTasks() = callbackFlow {
        tasksRef?.let {
            val snapshotListener = tasksRef.whereEqualTo("isActive", true).addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val tasks = snapshot.toObjects(CKTask::class.java)
                    Response.Success(tasks)
                } else {
                    Response.Error(e)
                }
                trySend(response).isSuccess
            }
            awaitClose {
                snapshotListener.remove()
            }
        }
    }

    override fun getTaskLogs() = callbackFlow {
        taskLogRef?.let {
            val snapshotListener = taskLogRef.addSnapshotListener { snapshot, e ->
                val response = if (snapshot != null) {
                    val tasks = snapshot.toObjects(CKTaskLog::class.java)
                    Response.Success(tasks)
                } else {
                    Response.Error(e)
                }
                trySend(response).isSuccess
            }
            awaitClose {
                snapshotListener.remove()
            }
        }
    }

    override suspend fun uploadTaskLog(log: CKTaskLog) = flow {
        taskLogRef?.let {
            try {
                emit(Response.Loading)
                val upload = taskLogRef.document().set(log).await()
                emit(Response.Success(upload))
            } catch (e: Exception) {
                emit(Response.Error(e))
            }
        }
    }
}