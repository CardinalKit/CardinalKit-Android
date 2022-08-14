package edu.stanford.cardinalkit.domain.repositories

import edu.stanford.cardinalkit.domain.models.Contact
import edu.stanford.cardinalkit.domain.models.Response
import kotlinx.coroutines.flow.Flow

interface ContactsRepository {
    fun getContacts(): Flow<Response<List<Contact>>>
}