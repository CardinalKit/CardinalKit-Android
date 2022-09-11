package edu.stanford.cardinalkit.domain.use_cases.auth

import edu.stanford.cardinalkit.domain.repositories.AuthRepository

class UpdateLastActive(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = repository.updateLastActive()
}
