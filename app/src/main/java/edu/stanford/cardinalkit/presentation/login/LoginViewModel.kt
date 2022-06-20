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
import edu.stanford.cardinalkit.domain.models.CKResult
import edu.stanford.cardinalkit.domain.repositories.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
    val client: SignInClient
): ViewModel() {
    val isAuthenticated get() = repository.isAuthenticated()

    private val _oneTapSignInState = mutableStateOf<CKResult<BeginSignInResult>>(CKResult.Success(null))
    val oneTapSignInState: State<CKResult<BeginSignInResult>> = _oneTapSignInState

    private val _signInState = mutableStateOf<CKResult<Boolean>>(CKResult.Success(null))
    val signInState: State<CKResult<Boolean>> = _signInState

    private val _saveUserState = mutableStateOf<CKResult<Boolean>>(CKResult.Success(null))
    val saveUserState: State<CKResult<Boolean>> = _saveUserState

    fun oneTapSignIn() {
        viewModelScope.launch {
            repository.oneTapSignInWithGoogle().collect { CKResult ->
                _oneTapSignInState.value = CKResult
            }
        }
    }

    fun signInWithGoogle(googleCredential: AuthCredential) {
        viewModelScope.launch {
            repository.firebaseSignInWithGoogle(googleCredential).collect { CKResult ->
                _signInState.value = CKResult
            }
        }
    }

    fun saveUser() {
        viewModelScope.launch {
            repository.saveUser().collect { CKResult ->
                _saveUserState.value = CKResult
            }
        }
    }

    fun getAuthStatus() = liveData(Dispatchers.IO) {
        repository.getAuthStatus().collect { CKResult ->
            emit(CKResult)
        }
    }
}