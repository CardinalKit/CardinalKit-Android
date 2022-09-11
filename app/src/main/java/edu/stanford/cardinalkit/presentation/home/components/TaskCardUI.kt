package edu.stanford.cardinalkit.presentation.home.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.presentation.tasks.TasksViewModel

@Composable
fun TaskCardUI(
    viewModel: TasksViewModel = hiltViewModel(),
) {
    if (viewModel.totalTasksToday.value > 0) {
        val annotatedString1 =
            AnnotatedString.Builder(
                "${viewModel.totalTasksCompleteToday.value} of ${viewModel.totalTasksToday.value} ${
                stringResource(
                    R.string.complete
                )
                }"
            )
                .apply {
                    addStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.onPrimary,
                        ),
                        0, 6
                    )
                }
        Card(
            backgroundColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .fillMaxWidth(),
            elevation = 0.dp,
            shape = RoundedCornerShape(18)
        ) {
            Row(
                modifier = Modifier.padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.task_progress),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Filled.Assignment,
                            contentDescription = "Complete a survey",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = annotatedString1.toAnnotatedString(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = stringResource(R.string.motivational_message),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                val num =
                    viewModel.totalTasksCompleteToday.value.toFloat() / viewModel.totalTasksToday.value.toFloat()
                TaskProgressBar(percentage = num * 100)
            }
        }
    }
}

@Composable
fun TaskProgressBar(percentage: Float) {
    Box(
        modifier = Modifier.size(120.dp),
        contentAlignment = Center
    ) {
        val onPrimaryColor = MaterialTheme.colorScheme.onPrimary
        val tertiaryColor = MaterialTheme.colorScheme.tertiary
        Canvas(
            modifier = Modifier
                .size(100.dp)
                .padding(6.dp)
        ) {
            drawCircle(
                SolidColor(onPrimaryColor),
                size.width / 2,
                style = Stroke(26f)
            )
            val convertedValue = (percentage / 100) * 360
            drawArc(
                brush = Brush.linearGradient(
                    colors = listOf(tertiaryColor, tertiaryColor)
                ),
                startAngle = -90f,
                sweepAngle = convertedValue.toFloat(),
                useCenter = false,
                style = Stroke(26f, cap = StrokeCap.Round)
            )
        }

        val percentDoneString =
            AnnotatedString.Builder("${percentage.toInt()}%\n${stringResource(R.string.done)}")

        Text(
            text = percentDoneString.toAnnotatedString(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}
