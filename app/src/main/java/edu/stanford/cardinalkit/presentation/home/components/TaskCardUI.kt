package edu.stanford.cardinalkit.presentation.home.components


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Color
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
import edu.stanford.cardinalkit.ui.theme.PrimaryTheme


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
                            color = MaterialTheme.colorScheme.primary,
                        ), 0, 6
                    )
                }
        Card(
            backgroundColor = MaterialTheme.colorScheme.surface,
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
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Filled.Assignment,
                            contentDescription = "Complete a survey",
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = annotatedString1.toAnnotatedString(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = stringResource(R.string.motivational_message),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onSurface
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
                    colors = listOf(PrimaryTheme, PrimaryTheme)
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
            color = MaterialTheme.colorScheme.primary
        )
    }
}



