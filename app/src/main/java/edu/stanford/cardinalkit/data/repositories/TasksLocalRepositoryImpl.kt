package edu.stanford.cardinalkit.data.repositories

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import edu.stanford.cardinalkit.domain.models.Contact
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.domain.models.tasks.CKTask
import edu.stanford.cardinalkit.domain.repositories.TasksRepository
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TasksLocalRepositoryImpl @Inject constructor(
    private val context: Context
) : TasksRepository {
    override fun getTasks() = flow {
        lateinit var jsonString: String

        emit(Response.Loading)
        // Load task data from JSON file in assets
        try {
            jsonString = context.assets.open("tasks.json")
                .bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            emit(Response.Error(e))
        }

        // Deserialize JSON into a list of CKTasks
        try {
            val listTasksType = object : TypeToken<List<CKTask>>() {}.type
            val contacts: List<CKTask> = Gson().fromJson(jsonString, listTasksType)
            emit(Response.Success(contacts))
        } catch (e: JsonParseException){
            emit(Response.Error(e))
        }
    }
}