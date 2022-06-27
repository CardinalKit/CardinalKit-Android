package edu.stanford.cardinalkit.presentation.tasks.components

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
        modifier = Modifier.padding(10.dp).fillMaxWidth(),
        onClick = {
            launchSurvey(surveyName, context)
        }
    ){
        Column(
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                text = surveyName,
                fontSize = 30.sp
            )
        }
    }
}