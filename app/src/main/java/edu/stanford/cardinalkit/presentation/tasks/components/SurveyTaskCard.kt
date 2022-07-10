package edu.stanford.cardinalkit.presentation.tasks.components

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurveyTaskCard(
    title: String,
    surveyName: String,
    context: Context,
    launchSurvey: (surveyName: String, context: Context) -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.padding(8.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.White),
        onClick = {
            launchSurvey(surveyName, context)
        },
    ){
        Column(
            modifier = Modifier.fillMaxWidth()
        ){
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = {
                        launchSurvey(surveyName, context)
                    }
                ){
                    Icon(imageVector = Icons.Filled.Assignment, tint= Color(0xFF484965),contentDescription = "Complete a survey")
                }
                Column(
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    Text(
                        text = title,
                        modifier = Modifier.padding(all=7.dp),
                        fontSize = 15.sp
                    )
                }

            }
        }
    }
}