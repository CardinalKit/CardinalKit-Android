package edu.stanford.cardinalkit.domain.usecases.auth

import edu.stanford.cardinalkit.domain.repositories.AuthRepository

class SaveUser(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = repository.saveUser()
}
