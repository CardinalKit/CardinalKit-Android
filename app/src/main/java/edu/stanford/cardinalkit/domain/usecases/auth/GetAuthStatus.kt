package edu.stanford.cardinalkit.domain.usecases.auth

import edu.stanford.cardinalkit.domain.repositories.AuthRepository

class GetAuthStatus(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.getAuthStatus()
}
