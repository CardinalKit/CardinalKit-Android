package edu.stanford.cardinalkit.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.stanford.cardinalkit.common.Constants
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.domain.repositories.AuthRepository
import edu.stanford.cardinalkit.domain.use_cases.auth.AuthUseCases
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RegisterViewModel @Inject constructor(
    @Named(Constants.AUTH_USE_CASES)
    private val useCases: AuthUseCases
): ViewModel() {

    private val _signUpState = mutableStateOf<Response<Boolean>>(Response.Success(null))
    val signUpState: State<Response<Boolean>> = _signUpState

    private val _saveUserState = mutableStateOf<Response<Boolean>>(Response.Success(null))
    val saveUserState: State<Response<Boolean>> = _saveUserState

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            useCases.signUpWithEmail(email, password).collect() { result ->
                _signUpState.value = result
            }
        }
    }

    fun saveUser() = viewModelScope.launch {
        useCases.saveUser().collect { response ->
            _saveUserState.value = response
        }
    }
}