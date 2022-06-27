package edu.stanford.cardinalkit.presentation.surveys

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.domain.use_cases.UseCases
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class SurveyViewModel @Inject constructor(
    @Named("useCases") private val useCases: UseCases
): ViewModel() {

    private val _surveyUploadedState = mutableStateOf<Response<Void?>>(Response.Success(null))
    val surveyUploadedState: State<Response<Void?>> = _surveyUploadedState

    fun uploadSurvey(name: String, data: String) = viewModelScope.launch {
        useCases.uploadSurvey(name, data).collect { response ->
            _surveyUploadedState.value = response
        }
    }
}