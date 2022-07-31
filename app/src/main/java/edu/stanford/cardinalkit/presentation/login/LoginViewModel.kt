package edu.stanford.cardinalkit.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.domain.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
    val client: SignInClient
): ViewModel() {
    private val _oneTapSignInState = mutableStateOf<Response<BeginSignInResult>>(Response.Success(null))
    val oneTapSignInState: State<Response<BeginSignInResult>> = _oneTapSignInState

    private val _signInState = mutableStateOf<Response<Boolean>>(Response.Success(null))
    val signInState: State<Response<Boolean>> = _signInState

    private val _saveUserState = mutableStateOf<Response<Boolean>>(Response.Success(null))
    val saveUserState: State<Response<Boolean>> = _saveUserState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            repository.signIn(email, password).collect { result ->
                _signInState.value = result
            }
        }
    }

    fun oneTapSignIn() {
        viewModelScope.launch {
            repository.oneTapSignInWithGoogle().collect { result ->
                _oneTapSignInState.value = result
            }
        }
    }

    fun signInWithGoogle(googleCredential: AuthCredential) {
        viewModelScope.launch {
            repository.firebaseSignInWithGoogle(googleCredential).collect { result ->
                _signInState.value = result
            }
        }
    }

    fun saveUser() {
        viewModelScope.launch {
            repository.saveUser().collect { result ->
                _saveUserState.value = result
            }
        }
    }

    fun getAuthStatus() = liveData(Dispatchers.IO) {
        repository.getAuthStatus().collect { result ->
            emit(result)
        }
    }
}