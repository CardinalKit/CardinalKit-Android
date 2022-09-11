package edu.stanford.cardinalkit.domain.usecases.auth

import edu.stanford.cardinalkit.domain.repositories.AuthRepository

class OneTapSignIn(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = repository.oneTapSignInWithGoogle()
}
