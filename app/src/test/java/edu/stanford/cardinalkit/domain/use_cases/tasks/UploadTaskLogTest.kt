package edu.stanford.cardinalkit.domain.use_cases.tasks

import com.google.common.truth.Truth.assertThat
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.domain.models.tasks.CKTaskLog
import edu.stanford.cardinalkit.domain.repositories.TasksRepository
import edu.stanford.cardinalkit.domain.use_cases.tasklogs.UploadTaskLog
import edu.stanford.cardinalkit.domain.use_cases.tasks.data.repository.FakeTasksRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.util.*

class UploadTaskLogTest {
    private lateinit var uploadTaskLog: UploadTaskLog
    private lateinit var fakeRepository: TasksRepository

    @Before
    fun setUp() {
        fakeRepository = FakeTasksRepository()
        uploadTaskLog = UploadTaskLog(fakeRepository)
    }

    @Test
    fun uploadTaskLogTest() = runBlocking {
        val log = CKTaskLog(
            taskID = "XE7VeC86W2qU9J8Q3C0A",
            date = Date()
        )

        val response = uploadTaskLog(log).first()
        assertThat(response).isInstanceOf(Response.Success::class.java)
    }

}