package edu.stanford.cardinalkit.domain.use_cases.auth

data class AuthUseCases (
    val signInWithEmail: SignInWithEmail,
    val signUpWithEmail: SignUpWithEmail,
    val oneTapSignIn: OneTapSignIn,
    val signInWithGoogle: SignInWithGoogle,
    val saveUser: SaveUser,
    val updateLastActive: UpdateLastActive,
    val getAuthStatus: GetAuthStatus,
    val resetPassword: ResetPassword
)