package edu.stanford.cardinalkit.data.repositories

import com.google.firebase.firestore.CollectionReference
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.domain.models.tasks.CKTask
import edu.stanford.cardinalkit.domain.repositories.TasksRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TasksRepositoryImpl @Inject constructor(
    private val tasksRef: CollectionReference?
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
}