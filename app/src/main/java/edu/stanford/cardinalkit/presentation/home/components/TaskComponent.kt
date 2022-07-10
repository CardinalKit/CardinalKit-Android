package edu.stanford.cardinalkit.presentation.home.components

import android.content.Intent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import edu.stanford.cardinalkit.common.Constants
import edu.stanford.cardinalkit.presentation.surveys.SurveyActivity
import edu.stanford.cardinalkit.presentation.tasks.TasksViewModel
import edu.stanford.cardinalkit.presentation.home.components.SurveyTaskCard

@Preview
@Composable
fun TaskComponent(
    viewModel: TasksViewModel = hiltViewModel()
)
{
    val context = LocalContext.current

    // TODO: Replace hardcoded data with surveys from DB
    val surveys = arrayOf<String>(
        "paginated_layout_questionnaire.json",
        "single_choice_questionnaire.json",
        "slider_questionnaire.json"
    )
    LazyColumn ( userScrollEnabled = true
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