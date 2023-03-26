package edu.stanford.cardinalkit.presentation.surveys

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.google.android.fhir.datacapture.QuestionnaireFragment
import dagger.hilt.android.AndroidEntryPoint
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.common.Constants
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.domain.models.tasks.CKTaskLog
import edu.stanford.cardinalkit.presentation.profile.ProfileViewModel
import edu.stanford.cardinalkit.presentation.tasks.TasksViewModel
import org.hl7.fhir.r4.model.QuestionnaireResponse
import java.util.*

@AndroidEntryPoint
class SurveyActivity : AppCompatActivity() {
    private var surveyName: String? = null // filename of the survey
    private var taskID: String? = null // id of the task
    private val surveyViewModel by viewModels<SurveyViewModel>()
    private val tasksViewModel by viewModels<TasksViewModel>()
    private val profileViewModel by viewModels<ProfileViewModel>()

    companion object {
        const val QUESTIONNAIRE_FRAGMENT_TAG = "questionnaire_fragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fhir_survey)

        val context = applicationContext

        supportFragmentManager.setFragmentResultListener(
            QuestionnaireFragment.SUBMIT_REQUEST_KEY,
            this
        ) { _, _ ->
            submitSurvey()
        }

        // Gets the filename of the FHIR survey JSON that was passed in
        // and creates the survey views
        surveyName = intent.getStringExtra(Constants.SURVEY_NAME)
        surveyName?.let { surveyViewModel.getSurvey(it) }

        // Gets the task ID in order to report back if the survey was
        // completed
        taskID = intent.getStringExtra(Constants.TASK_ID)

        // Observes result of survey submission
        surveyViewModel.surveyResultUploadedState.observe(this) {
            when (it) {
                is Response.Loading -> {}
                is Response.Success -> {
                    val log = taskID?.let { id -> CKTaskLog(id) }
                    if (log != null) {
                        tasksViewModel.uploadTaskLog(log)
                    }
                    finish()
                }
                is Response.Error -> {
                    Toast.makeText(context, it.e?.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Observes state of survey download
        surveyViewModel.surveyDownloadState.observe(this) {
            when (it) {
                is Response.Loading -> {}
                is Response.Success -> {
                    if (savedInstanceState == null && it.data != null) {
                        supportFragmentManager.commit {
                            setReorderingAllowed(true)
                            add(
                                R.id.fragment_container_view,
                                QuestionnaireFragment.builder().setQuestionnaire(it.data).build(),
                                QUESTIONNAIRE_FRAGMENT_TAG
                            )
                        }
                    }
                }
                is Response.Error -> {
                    Toast.makeText(
                        context,
                        R.string.error_loading_survey_message,
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Adds the submit button to the menu
        menuInflater.inflate(R.menu.submit_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Reacts to selecting submit from the menu
        if (item.itemId == R.id.submit) {
            submitSurvey()
        }

        // React to selecting cancel from the menu
        if (item.itemId == R.id.cancel) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun submitSurvey() {
        // Get the survey results from QuestionnaireFragment and upload to cloud
        val fragment = supportFragmentManager.findFragmentByTag(QUESTIONNAIRE_FRAGMENT_TAG)
            as QuestionnaireFragment
        val questionnaireResponse = fragment.getQuestionnaireResponse()
        val userID = profileViewModel.userID

        // Add metadata to QuestionnaireResponse
        questionnaireResponse.authored = Date()
        questionnaireResponse.id = UUID.randomUUID().toString()
        questionnaireResponse.status = QuestionnaireResponse.QuestionnaireResponseStatus.COMPLETED
        questionnaireResponse.subject.reference = "Patient/$userID"

        // Upload the survey to the database
        surveyViewModel.uploadSurveyResult(questionnaireResponse)
    }
}
