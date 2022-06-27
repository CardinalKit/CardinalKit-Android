package edu.stanford.cardinalkit.presentation.surveys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import ca.uhn.fhir.context.FhirContext
import ca.uhn.fhir.context.FhirVersionEnum
import com.google.android.fhir.datacapture.QuestionnaireFragment
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.common.Constants
import edu.stanford.cardinalkit.domain.models.Survey

class SurveyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fhir_survey)

        val viewModel by viewModels<SurveyViewModel>()

        val submitButton: Button = findViewById(R.id.button_submit)
        submitButton.setOnClickListener {
            submitSurvey()
        }

        val surveyName = intent.getStringExtra(Constants.SURVEY_NAME)

        if (surveyName != null) {

            val questionnaireJsonString =
                application.assets.open(surveyName)
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
        menuInflater.inflate(R.menu.submit_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.submit) {
            // save the survey data
            submitSurvey()

            // Go back to other activity
            finish()

            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun submitSurvey() {
        val viewModel by viewModels<SurveyViewModel>()

        // Get a questionnaire response
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view)
                as QuestionnaireFragment
        val questionnaireResponse = fragment.getQuestionnaireResponse()

        // Print the response to the log
        val jsonParser = FhirContext.forCached(FhirVersionEnum.R4).newJsonParser()
        val questionnaireResponseString =
            jsonParser.encodeResourceToString(questionnaireResponse)
        Log.d("response", questionnaireResponseString)

        // Sends the response to the server
        viewModel.uploadSurvey("name", questionnaireResponseString)

        finish()
    }

}