package edu.stanford.cardinalkit.domain.use_cases.tasks

import com.google.common.truth.Truth.assertThat
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.domain.models.tasks.*
import edu.stanford.cardinalkit.domain.repositories.TasksRepository
import edu.stanford.cardinalkit.domain.use_cases.tasks.data.repository.FakeTasksRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.util.*

class GetTaskLogsTest {
    private lateinit var getTaskLogs: GetTaskLogs
    private lateinit var fakeRepository: TasksRepository

    @Before
    fun setUp() {
        fakeRepository = FakeTasksRepository()
        getTaskLogs = GetTaskLogs(fakeRepository)

        val taskLogsToInsert = mutableListOf<CKTaskLog>()

        val fakeTaskLog0 = CKTaskLog(
            taskID = "XE7VeC86W2qU9J8Q3C0A",
            date = Date()
        )

        val fakeTaskLog1 = CKTaskLog(
            taskID = "sIbsVjdnpkcaC5tjJwjQ",
            date = Date()
        )

        taskLogsToInsert.add(fakeTaskLog0)
        taskLogsToInsert.add(fakeTaskLog1)

        runBlocking {
            taskLogsToInsert.forEach {
                (fakeRepository as FakeTasksRepository).insertTaskLog(it)
            }
        }

    }

    @Test
    fun getTaskLogsTest() = runBlocking {
        val response = getTaskLogs().first()
        var taskLogs = listOf<CKTaskLog>()

        if (response is Response.Success && response.data != null){
            taskLogs = response.data as List<CKTaskLog>
        }

        assertThat(taskLogs[0].taskID).isEqualTo("XE7VeC86W2qU9J8Q3C0A")
        assertThat(taskLogs[1].taskID).isEqualTo("sIbsVjdnpkcaC5tjJwjQ")
    }

}