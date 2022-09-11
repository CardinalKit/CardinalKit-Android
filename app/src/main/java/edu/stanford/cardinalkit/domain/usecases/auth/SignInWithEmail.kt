package edu.stanford.cardinalkit.domain.usecases.auth

import edu.stanford.cardinalkit.domain.repositories.AuthRepository

class SignInWithEmail(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ) = repository.signIn(email, password)
}
