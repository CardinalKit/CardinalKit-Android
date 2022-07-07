package edu.stanford.cardinalkit.presentation.tasks

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.common.Constants
import edu.stanford.cardinalkit.presentation.surveys.SurveyActivity
import edu.stanford.cardinalkit.presentation.tasks.components.SurveyTaskCard
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    viewModel: TasksViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.tasks_screen_title),
                        modifier = Modifier.padding(10.dp),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Light
                    )
                },
                backgroundColor = Color(0xFFF1F1F1),
                contentColor = Color.Black)

        },
        containerColor =  Color(0xFFF5F5F5),
        content = { contentPadding ->
            val context = LocalContext.current

            // TODO: Replace hardcoded data with surveys from DB
            val surveys = arrayOf<String>(
                "paginated_layout_questionnaire.json",
                "single_choice_questionnaire.json",
                "slider_questionnaire.json"
            )

            Column(modifier = Modifier.padding(horizontal = 10.dp).padding(top=20.dp)){
                LazyColumn (
                    modifier = Modifier.fillMaxSize().padding(contentPadding)
                ){
                    items(surveys) { survey ->
                        SurveyTaskCard(
                            surveyName = survey,
                            context = context,
                            launchSurvey = { surveyName, context ->
                                // Launches the SurveyActivity and passes the survey name to display
                                val intent = Intent(context, SurveyActivity::class.java).apply {
                                    putExtra(Constants.SURVEY_NAME, surveyName)
                                }
                                context.startActivity(intent)
                            }
                        )
                    }
                }
            }
        }
    )
    fun launchSurvey(surveyName: String, context: Context){
        val intent = Intent(context, SurveyActivity::class.java).apply {
            putExtra(Constants.SURVEY_NAME, surveyName)
        }
        context.startActivity(intent)
    }
}

