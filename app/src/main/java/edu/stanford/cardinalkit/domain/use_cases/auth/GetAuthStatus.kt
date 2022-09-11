package edu.stanford.cardinalkit.domain.use_cases.auth

import edu.stanford.cardinalkit.domain.repositories.AuthRepository

class GetAuthStatus(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.getAuthStatus()
}
