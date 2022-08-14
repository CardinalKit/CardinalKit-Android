package edu.stanford.cardinalkit.presentation.tasks.components

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.common.Constants
import edu.stanford.cardinalkit.common.toLocalDate
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.domain.models.tasks.CKTaskCategory
import edu.stanford.cardinalkit.presentation.surveys.SurveyActivity
import edu.stanford.cardinalkit.presentation.tasks.TasksViewModel
import edu.stanford.cardinalkit.ui.theme.PrimaryTheme
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCard(
    id: String,
    title: String,
    description: String,
    category: CKTaskCategory,
    uri: String,
    viewModel: TasksViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    // check the task logs to see if this task has been completed
    var completed = remember {
        mutableStateOf(false)
    }

    when(val response = viewModel.taskLogsState.value) {
        is Response.Success -> {
            response.data?.let { taskLogs ->
                completed.value = taskLogs.any { log ->
                    log.taskID == id
                            && log.date.toLocalDate() == viewModel.currentDate.value
                }
            }
        }
        else -> completed.value = false
    }

    fun launchSurvey(surveyName: String, taskID: String){
        val intent = Intent(context, SurveyActivity::class.java).apply {
            putExtra(Constants.SURVEY_NAME, surveyName)
            putExtra(Constants.TASK_ID, taskID)
        }
        context.startActivity(intent)
    }

    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.padding(8.dp).fillMaxWidth().clickable {
            if(viewModel.currentDate.value == LocalDate.now()) {
                when (category) {
                    CKTaskCategory.SURVEY -> {
                        if (!completed.value) {
                            launchSurvey(surveyName = uri, taskID = id)
                        } else {
                            Toast.makeText(context, R.string.task_already_completed, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(context, R.string.task_not_scheduled_today, Toast.LENGTH_SHORT).show()
            }
        },
        colors = CardDefaults.cardColors(Color.White)
    ){
        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical=5.dp)
        ){
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center
                ){
                    if(completed.value){
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            tint = PrimaryTheme,
                            contentDescription = "Task Completed",
                            modifier = Modifier.padding(15.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.Circle,
                            tint = PrimaryTheme,
                            contentDescription = "Complete a task",
                            modifier = Modifier.padding(15.dp)
                        )
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    Text(
                        text = title,
                        modifier = Modifier.padding(horizontal=7.dp),
                        fontSize = 20.sp
                    )
                    Text(
                        text = description,
                        modifier = Modifier.padding(horizontal=7.dp),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}