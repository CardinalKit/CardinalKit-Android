package edu.stanford.cardinalkit.presentation.register

import android.text.TextUtils
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.stanford.cardinalkit.common.Constants
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.domain.use_cases.auth.AuthUseCases
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class RegisterViewModel @Inject constructor(
    @Named(Constants.AUTH_USE_CASES)
    private val useCases: AuthUseCases
): ViewModel() {

    var signUpState = mutableStateOf<Response<Boolean>>(Response.Success(null))
        private set

    var saveUserState = mutableStateOf<Response<Boolean>>(Response.Success(null))
        private set

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            useCases.signUpWithEmail(email, password).collect() { result ->
                signUpState.value = result
            }
        }
    }

    fun saveUser() = viewModelScope.launch {
        useCases.saveUser().collect { response ->
            saveUserState.value = response
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

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}