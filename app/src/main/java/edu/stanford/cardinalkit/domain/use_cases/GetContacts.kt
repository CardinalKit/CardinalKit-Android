package edu.stanford.cardinalkit.domain.use_cases

import edu.stanford.cardinalkit.domain.repositories.ContactsRepository

class GetContacts (
    private val repository: ContactsRepository
    ) {
    operator fun invoke() = repository.getContacts()
}