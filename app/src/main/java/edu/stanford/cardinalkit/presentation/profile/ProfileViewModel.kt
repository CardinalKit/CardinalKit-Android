package edu.stanford.cardinalkit.presentation.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.domain.repositories.AuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    val userID get() = repository.getUserID()

    var signOutState = mutableStateOf<Response<Boolean>>(Response.Success(false))
        private set

    fun signOut() {
        viewModelScope.launch {
            repository.signOut().collect { response ->
                signOutState.value = response
            }
        }
    }
}
