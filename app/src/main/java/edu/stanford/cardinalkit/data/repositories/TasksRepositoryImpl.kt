package edu.stanford.cardinalkit.data.repositories

import android.content.Context
import com.google.firebase.firestore.CollectionReference
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import edu.stanford.cardinalkit.common.Config
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
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class TasksRepositoryImpl @Inject constructor(
    @Named(Constants.TASKS_REF)
    private val tasksRef: CollectionReference?,
    private val context: Context
) : TasksRepository {
    override fun getTasks() =
        if (Config.REMOTE_TASKS) {
            callbackFlow {
                tasksRef?.let {
                    val snapshotListener =
                        tasksRef.whereEqualTo("isActive", true).addSnapshotListener { snapshot, e ->
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
        } else {
            flow {
                lateinit var jsonString: String

                emit(Response.Loading)
                // Load task data from JSON file in assets
                try {
                    jsonString = context.assets.open(Config.TASKS_FILE)
                        .bufferedReader().use { it.readText() }
                } catch (e: IOException) {
                    emit(Response.Error(e))
                }

                // Deserialize JSON into a list of CKTasks
                try {
                    val listTasksType = object : TypeToken<List<CKTask>>() {}.type
                    val tasks: List<CKTask> = Gson().fromJson(jsonString, listTasksType)
                    emit(Response.Success(tasks))
                } catch (e: JsonParseException) {
                    emit(Response.Error(e))
                }
            }
        }
}