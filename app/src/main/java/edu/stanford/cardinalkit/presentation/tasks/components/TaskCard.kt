package edu.stanford.cardinalkit.presentation.tasks.components

import android.content.Context
import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.stanford.cardinalkit.common.Constants
import edu.stanford.cardinalkit.domain.models.tasks.CKTask
import edu.stanford.cardinalkit.domain.models.tasks.CKTaskCategory
import edu.stanford.cardinalkit.domain.models.tasks.CKTaskContext
import edu.stanford.cardinalkit.presentation.surveys.SurveyActivity
import edu.stanford.cardinalkit.ui.theme.PrimaryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCard(
    title: String,
    description: String,
    category: CKTaskCategory,
    uri: String
) {

    val context = LocalContext.current

    fun launchSurvey(surveyName: String){
        val intent = Intent(context, SurveyActivity::class.java).apply {
            putExtra(Constants.SURVEY_NAME, surveyName)
        }
        context.startActivity(intent)
    }

    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.padding(8.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.White)
    ){
        Column(
            modifier = Modifier.fillMaxWidth()
        ){
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = {
                        when(category){
                            CKTaskCategory.SURVEY -> launchSurvey(uri)
                            CKTaskCategory.MISC -> print("No type specific")
                        }
                    }
                ){
                    Icon(imageVector = Icons.Filled.Assignment, tint= PrimaryTheme,contentDescription = "Complete a survey")
                }
                Column(
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    Text(
                        text = title,
                        modifier = Modifier.padding(all=7.dp),
                        fontSize = 15.sp
                    )
                    Text(
                        text = description,
                        modifier = Modifier.padding(all=7.dp),
                        fontSize = 12.sp
                    )
                    Text(
                        text = category.name,
                        modifier = Modifier.padding(all=7.dp),
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}