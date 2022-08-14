package edu.stanford.cardinalkit.presentation.surveys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import ca.uhn.fhir.context.FhirContext
import ca.uhn.fhir.context.FhirVersionEnum
import com.google.android.fhir.datacapture.QuestionnaireFragment
import dagger.hilt.android.AndroidEntryPoint
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.common.Constants
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.domain.models.SurveyResult
import edu.stanford.cardinalkit.domain.models.tasks.CKTaskLog
import edu.stanford.cardinalkit.presentation.common.ProgressIndicator
import edu.stanford.cardinalkit.presentation.tasks.TasksViewModel
import java.time.LocalDate
import java.util.*

@AndroidEntryPoint
class SurveyActivity : AppCompatActivity() {

    private var surveyName: String? = null // filename of the survey
    private var taskID: String? = null // id of the task
    val surveyViewModel by viewModels<SurveyViewModel>()
    private val tasksViewModel by viewModels<TasksViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fhir_survey)

        val context = applicationContext

        // Adds a listener to the submit button
        val submitButton: Button = findViewById(R.id.button_submit)
        submitButton.setOnClickListener {
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
            when(it){
                is Response.Success -> {
                    val log = taskID?.let { it -> CKTaskLog(it) }
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
            when(it){
                is Response.Success -> {
                    val arguments =
                        bundleOf(QuestionnaireFragment.EXTRA_QUESTIONNAIRE_JSON_STRING to it.data)
                    if (savedInstanceState == null) {
                        supportFragmentManager.commit {
                            setReorderingAllowed(true)
                            add<QuestionnaireFragment>(
                                R.id.fragment_container_view,
                                args = arguments
                            )
                        }
                    }
                }
                is Response.Error -> {
                    Toast.makeText(context, R.string.error_loading_survey_message, Toast.LENGTH_SHORT).show()
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
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view)
                as QuestionnaireFragment
        val questionnaireResponse = fragment.getQuestionnaireResponse()
        surveyName?.let { surveyViewModel.uploadSurveyResult(it, questionnaireResponse) }
    }
}