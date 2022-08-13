package edu.stanford.cardinalkit.domain.repositories

import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.AuthCredential
import edu.stanford.cardinalkit.domain.models.Response
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun isAuthenticated(): Boolean
    fun getAuthStatus(): Flow<Boolean>
    fun getFullName(): String
    suspend fun oneTapSignInWithGoogle(): Flow<Response<BeginSignInResult>>
    suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): Flow<Response<Boolean>>
    suspend fun signIn(email: String, password: String): Flow<Response<Boolean>>
    suspend fun signUp(email: String, password: String): Flow<Response<Boolean>>
    suspend fun signOut(): Flow<Response<Boolean>>
    suspend fun saveUser(): Flow<Response<Boolean>>
    suspend fun updateLastActive(): Flow<Response<Boolean>>
    suspend fun resetPassword(email: String): Flow<Response<Boolean>>
}