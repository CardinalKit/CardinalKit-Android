package edu.stanford.cardinalkit.presentation.contacts

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.stanford.cardinalkit.common.Constants
import edu.stanford.cardinalkit.domain.models.Contact
import edu.stanford.cardinalkit.domain.models.Response
import edu.stanford.cardinalkit.domain.use_cases.UseCases
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ContactsViewModel @Inject constructor(
    @Named(Constants.USE_CASES)
    private val useCases: UseCases
) : ViewModel() {

    private val _contactsState = mutableStateOf<Response<List<Contact>>>(Response.Loading)
    val contactsState: State<Response<List<Contact>>> = _contactsState

    init {
        getContacts()
    }

    private fun getContacts() = viewModelScope.launch {
        _contactsState.value = useCases.getContacts()
    }
}