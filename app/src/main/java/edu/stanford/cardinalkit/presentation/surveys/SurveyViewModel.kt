package edu.stanford.cardinalkit.presentation.surveys

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.uhn.fhir.context.FhirContext
import ca.uhn.fhir.context.FhirVersionEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.stanford.cardinalkit.common.Constants
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.domain.models.SurveyResult
import edu.stanford.cardinalkit.domain.use_cases.surveys.SurveysUseCases
import kotlinx.coroutines.launch
import org.hl7.fhir.r4.model.QuestionnaireResponse
import java.util.*
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class SurveyViewModel @Inject constructor(
    @Named(Constants.SURVEYS_USE_CASES) private val useCases: SurveysUseCases
): ViewModel() {

//    private val _surveyResultUploadedState = mutableStateOf<Response<Void?>>(Response.Loading)
//    val surveyResultUploadedState: State<Response<Void?>> = _surveyResultUploadedState

    private val _surveyResultUploadedState: MutableLiveData<Response<Void?>> = MutableLiveData(Response.Loading)
    val surveyResultUploadedState: LiveData<Response<Void?>> = _surveyResultUploadedState

    fun uploadSurveyResult(surveyName: String, questionnaireResponse: QuestionnaireResponse) = viewModelScope.launch {
        // Serialize the results from QuestionnaireResponse to JSON
        val jsonParser = FhirContext.forCached(FhirVersionEnum.R4).newJsonParser()
        val questionnaireResponseString =
            jsonParser.encodeResourceToString(questionnaireResponse)

        // Upload results to the database
        val surveyResult = SurveyResult(
            id = UUID.randomUUID().toString(),
            name = surveyName,
            data = questionnaireResponseString,
            date = Date()
        )

        useCases.uploadSurveyResult(surveyResult).collect { response ->
            _surveyResultUploadedState.value = response
        }
    }

    fun getSurvey(name: String): Response<String> {
        return useCases.getSurvey(name)
    }
}