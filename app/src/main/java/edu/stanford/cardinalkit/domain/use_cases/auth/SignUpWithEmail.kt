package edu.stanford.cardinalkit.domain.use_cases.auth

import edu.stanford.cardinalkit.domain.repositories.AuthRepository

class SignUpWithEmail(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ) = repository.signUp(email, password)
}
