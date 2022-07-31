package edu.stanford.cardinalkit.presentation.surveys

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.stanford.cardinalkit.common.Constants
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.domain.use_cases.surveys.SurveysUseCases
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class SurveyViewModel @Inject constructor(
    @Named(Constants.SURVEYS_USE_CASES) private val useCases: SurveysUseCases
): ViewModel() {

    private val _surveyUploadedState = mutableStateOf<Response<Void?>>(Response.Success(null))
    val surveyUploadedState: State<Response<Void?>> = _surveyUploadedState

    fun uploadSurvey(name: String, data: String) = viewModelScope.launch {
        useCases.uploadSurvey(name, data).collect { response ->
            _surveyUploadedState.value = response
        }
    }

    fun getSurvey(name: String): Response<String> {
        return useCases.getSurvey(name)
    }
}