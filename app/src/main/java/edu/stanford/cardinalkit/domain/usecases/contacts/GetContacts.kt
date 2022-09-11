package edu.stanford.cardinalkit.domain.usecases.contacts

import edu.stanford.cardinalkit.domain.repositories.ContactsRepository

class GetContacts(
    private val repository: ContactsRepository
) {
    operator fun invoke() = repository.getContacts()
}
