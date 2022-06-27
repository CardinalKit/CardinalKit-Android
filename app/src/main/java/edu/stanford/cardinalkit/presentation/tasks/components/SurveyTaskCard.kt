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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurveyTaskCard(
    surveyName: String,
    context: Context,
    launchSurvey: (surveyName: String, context: Context) -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.padding(8.dp).fillMaxWidth(),
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
                Column(
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text(
                        text = surveyName,
                        fontSize = 20.sp
                    )
                }
                IconButton(
                    onClick = {
                        launchSurvey(surveyName, context)
                    }
                ){
                    Icon(imageVector = Icons.Filled.Assignment, contentDescription = "Complete a survey")
                }
            }
        }
    }
}