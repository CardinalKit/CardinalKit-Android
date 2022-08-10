package edu.stanford.cardinalkit.domain.use_cases.auth

import edu.stanford.cardinalkit.domain.repositories.AuthRepository

class ResetPassword(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
    ) = repository.resetPassword(email)
}