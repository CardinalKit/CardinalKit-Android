package edu.stanford.cardinalkit.presentation.home.components

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.common.Constants
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.presentation.tasks.TasksViewModel
import edu.stanford.cardinalkit.ui.theme.PrimaryTheme


@Composable
fun TaskCardUI(

) {

    val annotatedString1 = AnnotatedString.Builder("1/2 Task")
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



            TaskProgressBar(percentage = 50f)


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
                sweepAngle = convertedValue,
                useCenter = false,
                style = Stroke(26f, cap = StrokeCap.Round)
            )
        }

        val annotatedString2 = AnnotatedString.Builder("${percentage.toInt()}%\nDone")
            .apply {
                addStyle(
                    SpanStyle(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal
                    ), 4, 8
                )
            }

        Text(
            text = annotatedString2.toAnnotatedString(),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

    }

}



