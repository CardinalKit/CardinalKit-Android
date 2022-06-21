package edu.stanford.cardinalkit.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.google.android.fhir.datacapture.QuestionnaireFragment
import edu.stanford.cardinalkit.R

class SurveyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fhir_survey)

        val questionnaireJsonString =
            application.assets.open("screener-questionnaire.json")
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
    }
}