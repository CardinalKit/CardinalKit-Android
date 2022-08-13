package edu.stanford.cardinalkit.presentation.home.components


import androidx.compose.runtime.Composable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.stanford.cardinalkit.common.toLocalDate
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.presentation.tasks.TasksViewModel
import edu.stanford.cardinalkit.ui.theme.PrimaryTheme


@Composable
fun TaskCardUI(
    viewModel: TasksViewModel = hiltViewModel(),
) {

    // check the task logs to see if this task has been completed
    var counterComplete = 0
    var counterTask=0

    when(val response = viewModel.taskLogsState.value) {
        is Response.Success -> {
            response.data?.let { taskLogs ->
                for (log in taskLogs) {
                    if (log.date.toLocalDate() == viewModel.currentDate.value){
                        counterComplete++
                    }
                }
            }
        }

    }
    when(val response = viewModel.tasksState.value) {
        is Response.Success -> {
            response.data?.let { task ->
                for (log in task) {
                    if (log.schedule.isScheduledOn(viewModel.currentDate.value)) {
                        counterComplete++
                    }
                }
            }
        }
    }

    val completedTask= counterComplete
    val totalTask= counterTask

    val annotatedString1 = AnnotatedString.Builder("${completedTask}/${totalTask} Task")
        .apply {
            addStyle(
                SpanStyle(
                    color = PrimaryTheme,
                ), 0, 3
            )
        }

    Card(
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxWidth(),
        elevation = 0.dp,
        shape= RoundedCornerShape(18)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                Text(
                    text = "Task Progress",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Filled.Assignment,
                        contentDescription = "Complete a survey"
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = annotatedString1.toAnnotatedString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Keep up the good work,\nyou got this",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            }

            var num = completedTask.toFloat()/ totalTask
            TaskProgressBar(percentage = num*100)
        }
    }
}

@Composable
fun TaskProgressBar(percentage: Float) {
    Box(
        modifier = Modifier.size(120.dp),
        contentAlignment = Center
    ) {
        Canvas(
            modifier = Modifier
                .size(100.dp)
                .padding(6.dp)
        ) {

            drawCircle(
                SolidColor(Color(0xFFE3E5E7)),
                size.width / 2,
                style = Stroke(26f)
            )
            val convertedValue = (percentage / 100) * 360
            drawArc(
                brush = Brush.linearGradient(
                    colors = listOf(PrimaryTheme, Color.White)
                ),
                startAngle = -90f,
                sweepAngle = convertedValue.toFloat(),
                useCenter = false,
                style = Stroke(26f, cap = StrokeCap.Round)
            )
        }

        val annotatedString2 = AnnotatedString.Builder("${percentage}%\nDone")

        Text(
            text = annotatedString2.toAnnotatedString(),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}



