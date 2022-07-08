package edu.stanford.cardinalkit.domain.repositories

import edu.stanford.cardinalkit.domain.models.Contact

interface ContactsRepository {
    fun getContacts(): List<Contact>
}