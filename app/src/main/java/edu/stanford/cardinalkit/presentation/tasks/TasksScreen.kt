package edu.stanford.cardinalkit.presentation.tasks

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.presentation.surveys.SurveyActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.tasks_screen_title),
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White
                    )
                }
            )
        },
        content = { contentPadding ->
            val context = LocalContext.current

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Button(
                    modifier = Modifier.padding(20.dp),
                    onClick = {
                        openSurvey("single_choice_questionnaire.json", context)
                }){
                    Text("Take A Survey")
                }
            }
        }
    )
}

fun openSurvey(name: String, context: Context) {
    val intent = Intent(context, SurveyActivity::class.java).apply {
        putExtra("edu.stanford.cardinalkit.SURVEY_NAME", name)
    }
    context.startActivity(intent)
}