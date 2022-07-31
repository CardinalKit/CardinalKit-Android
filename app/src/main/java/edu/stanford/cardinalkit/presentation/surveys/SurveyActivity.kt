package edu.stanford.cardinalkit.presentation.surveys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.viewmodel.compose.viewModel
import ca.uhn.fhir.context.FhirContext
import ca.uhn.fhir.context.FhirVersionEnum
import com.google.android.fhir.datacapture.QuestionnaireFragment
import dagger.hilt.android.AndroidEntryPoint
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.common.Constants
import edu.stanford.cardinalkit.domain.models.Response
import java.io.IOException

@AndroidEntryPoint
class SurveyActivity : AppCompatActivity() {

    private var surveyName: String? = null // filename of the survey

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fhir_survey)

        val context = applicationContext
        val viewModel by viewModels<SurveyViewModel>()

        // Adds a listener to the submit button
        val submitButton: Button = findViewById(R.id.button_submit)
        submitButton.setOnClickListener {
            submitSurvey()
        }

        // Gets the filename of the FHIR survey JSON that was passed in
        // and creates the survey views
        surveyName = intent.getStringExtra(Constants.SURVEY_NAME)

        when (val response = surveyName?.let { viewModel.getSurvey(name = it) }) {
            is Response.Success -> {
                val arguments =
                    bundleOf(QuestionnaireFragment.EXTRA_QUESTIONNAIRE_JSON_STRING to response.data)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Adds the submit button to the menu
        menuInflater.inflate(R.menu.submit_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Reacts to selecting submit from the menu
        if (item.itemId == R.id.submit) {
            submitSurvey()
            finish()
        }
        if (item.itemId == R.id.cancel) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun submitSurvey() {
        val viewModel by viewModels<SurveyViewModel>()

        // Get the survey results from QuestionnaireFragment
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view)
                as QuestionnaireFragment
        val questionnaireResponse = fragment.getQuestionnaireResponse()

        // Serialize the results to JSON
        val jsonParser = FhirContext.forCached(FhirVersionEnum.R4).newJsonParser()
        val questionnaireResponseString =
            jsonParser.encodeResourceToString(questionnaireResponse)

        // Upload results to the database
        val surveyNameWithoutExtension = this.surveyName?.replace("\\.\\w+$".toRegex(), "")
        viewModel.uploadSurvey(surveyNameWithoutExtension ?: "unknown", questionnaireResponseString)

        // Return to main activity
        finish()
    }
}