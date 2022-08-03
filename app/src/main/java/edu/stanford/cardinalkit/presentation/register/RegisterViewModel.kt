package edu.stanford.cardinalkit.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.domain.repositories.AuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    private val _signUpState = mutableStateOf<Response<Boolean>>(Response.Success(null))
    val signUpState: State<Response<Boolean>> = _signUpState

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            repository.signUp(email, password).collect() { result ->
                _signUpState.value = result
            }
        }
    }

    fun isValidPassword(password: String): Boolean {
        if (password.length < 8) return false //minimum of 8 characters
        if (password.firstOrNull { it.isDigit() } == null) return false //1 number
        if (password.filter { it.isLetter() }.filter { it.isUpperCase() }.firstOrNull() == null) return false //1 uppercase
        if (password.filter { it.isLetter() }.filter { it.isLowerCase() }.firstOrNull() == null) return false //1 lowercase
        if (password.firstOrNull { !it.isLetterOrDigit() } == null) return false // special character

        return true
    }
}