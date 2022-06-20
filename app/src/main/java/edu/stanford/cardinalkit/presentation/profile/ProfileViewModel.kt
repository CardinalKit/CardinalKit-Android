package edu.stanford.cardinalkit.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.stanford.cardinalkit.domain.models.CKResult
import edu.stanford.cardinalkit.domain.repositories.AuthRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {
    val fullName get() = repository.getFullName()

    private val _signOutState = mutableStateOf<CKResult<Boolean>>(CKResult.Success(false))
    val signOutState: State<CKResult<Boolean>> = _signOutState

    fun signOut() {
        viewModelScope.launch {
            repository.signOut().collect { response ->
                _signOutState.value = response
            }
        }
    }
}