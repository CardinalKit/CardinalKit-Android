package edu.stanford.cardinalkit.presentation.tasks.components

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.common.Constants
import edu.stanford.cardinalkit.common.toLocalDate
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.domain.models.tasks.CKTask
import edu.stanford.cardinalkit.domain.models.tasks.CKTaskCategory
import edu.stanford.cardinalkit.domain.models.tasks.CKTaskLog
import edu.stanford.cardinalkit.presentation.surveys.SurveyActivity
import edu.stanford.cardinalkit.presentation.tasks.TasksViewModel
import java.time.LocalDate

@Composable
fun TaskCard(
    task: CKTask,
    viewModel: TasksViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    // check the task logs to see if this task has been completed
    var completed = remember {
        mutableStateOf(false)
    }

    when (val response = viewModel.taskLogsState.value) {
        is Response.Success -> {
            response.data?.let { taskLogs ->
                completed.value = taskLogs.any { log ->
                    log.taskID == task.id &&
                        log.date.toLocalDate() == viewModel.currentDate.value
                }
            }
        }
        else -> completed.value = false
    }

    fun launchSurvey(surveyName: String, taskID: String) {
        val intent = Intent(context, SurveyActivity::class.java).apply {
            putExtra(Constants.SURVEY_NAME, surveyName)
            putExtra(Constants.TASK_ID, taskID)
        }
        context.startActivity(intent)
    }

    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .shadow(elevation = 1.dp, shape = RoundedCornerShape(5.dp))
            .clickable {
                if (viewModel.currentDate.value == LocalDate.now()) {
                    when (task.context.category) {
                        CKTaskCategory.SURVEY -> {
                            if (!completed.value) {
                                launchSurvey(surveyName = task.context.uri, taskID = task.id)
                            } else {
                                Toast
                                    .makeText(
                                        context,
                                        R.string.task_already_completed,
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            }
                        }
                        CKTaskCategory.MISC -> viewModel.uploadTaskLog(CKTaskLog(task.id))
                        CKTaskCategory.STEPS -> {}
                    }
                } else {
                    Toast
                        .makeText(context, R.string.task_not_scheduled_today, Toast.LENGTH_SHORT)
                        .show()
                }
            },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    if (completed.value) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = "Task completed",
                            modifier = Modifier.padding(15.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.Circle,
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = "Complete a task",
                            modifier = Modifier.padding(15.dp)
                        )
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    Text(
                        text = task.title,
                        modifier = Modifier.padding(horizontal = 7.dp),
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = task.description,
                        modifier = Modifier.padding(horizontal = 7.dp),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    /**
                     * Additional widgets with information for specific task types
                     * can be inserted below here.
                     */
                    when (task.context.category) {
                        CKTaskCategory.STEPS -> {
                            // The progress should only be shown if the task is active today
                            if (viewModel.currentDate.value == LocalDate.now()) {
                                StepGoalProgress(
                                    task,
                                    completed.value
                                )
                            }
                        }
                        else -> {}
                    }
                }
            }
        }
    }
}
