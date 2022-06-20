package edu.stanford.cardinalkit.domain.repositories

import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.firebase.auth.AuthCredential
import edu.stanford.cardinalkit.domain.models.CKResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun isAuthenticated(): Boolean
    fun getAuthStatus(): Flow<Boolean>
    fun getFullName(): String
    suspend fun oneTapSignInWithGoogle(): Flow<CKResult<BeginSignInResult>>
    suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential): Flow<CKResult<Boolean>>
    suspend fun signOut(): Flow<CKResult<Boolean>>
    suspend fun saveUser(): Flow<CKResult<Boolean>>

}