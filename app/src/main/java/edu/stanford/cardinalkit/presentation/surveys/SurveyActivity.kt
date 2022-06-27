package edu.stanford.cardinalkit.presentation.surveys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.activity.viewModels
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

@AndroidEntryPoint
class SurveyActivity : AppCompatActivity() {

    private var surveyName: String? = null // filename of the survey

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fhir_survey)

        // Adds a listener to the submit button
        val submitButton: Button = findViewById(R.id.button_submit)
        submitButton.setOnClickListener {
            submitSurvey()
        }

        // Gets the filename of the FHIR survey JSON that was passed in
        // and creates the survey views
        surveyName = intent.getStringExtra(Constants.SURVEY_NAME)

        if (surveyName != null) {
            val questionnaireJsonString =
                application.assets.open(surveyName!!)
                    .bufferedReader().use { it.readText() }
            val arguments = bundleOf(
                QuestionnaireFragment.EXTRA_QUESTIONNAIRE_JSON_STRING to questionnaireJsonString
            )
            if (savedInstanceState == null) {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    add<QuestionnaireFragment>(R.id.fragment_container_view, args = arguments)
                }
            }
        } else {
            finish()
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

    fun submitSurvey() {
        val viewModel by viewModels<SurveyViewModel>()

        // Get the survey results from QuestionnaireFragment
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view)
                as QuestionnaireFragment
        val questionnaireResponse = fragment.getQuestionnaireResponse()
        val surveyName = intent.getStringExtra(Constants.SURVEY_NAME)

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