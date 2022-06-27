package edu.stanford.cardinalkit.data.repositories

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue.serverTimestamp
import edu.stanford.cardinalkit.domain.repositories.AuthRepository
import edu.stanford.cardinalkit.domain.models.CKResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl  @Inject constructor(
    private val auth: FirebaseAuth,
    private var oneTapClient: SignInClient,
    @Named("signInRequest")
    private var signInRequest: BeginSignInRequest,
    private var signInClient: GoogleSignInClient,
    @Named("usersRef")
    private val usersRef: CollectionReference
) : AuthRepository {
    override fun isAuthenticated() = auth.currentUser != null

    override suspend fun oneTapSignInWithGoogle() = flow {
        try {
            emit(CKResult.Loading)
            val result = oneTapClient.beginSignIn(signInRequest).await()
            emit(CKResult.Success(result))
        } catch (e: Exception) {
            emit(CKResult.Error(e))
        }
    }

    override suspend fun firebaseSignInWithGoogle(googleCredential: AuthCredential) = flow {
        try {
            emit(CKResult.Loading)
            val authResult = auth.signInWithCredential(googleCredential).await()
            val isNewUser = authResult.additionalUserInfo?.isNewUser
            emit(CKResult.Success(isNewUser))
        } catch (e: Exception) {
            emit(CKResult.Error(e))
        }
    }

    override fun getAuthStatus() = callbackFlow  {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    override suspend fun saveUser() = flow {
        try {
            emit(CKResult.Loading)
            auth.currentUser?.apply {
                usersRef.document(uid).set(mapOf(
                    "name" to displayName,
                    "email" to email,
                    "createdDate" to serverTimestamp()
                )).await()
                emit(CKResult.Success(true))
            }
        } catch (e: Exception) {
            emit(CKResult.Error(e))
        }
    }

    override fun getFullName() = auth.currentUser?.displayName.toString()

    override suspend fun signOut() = flow {
        try {
            emit(CKResult.Loading)
            auth.signOut()
            oneTapClient.signOut().await()
            emit(CKResult.Success(true))
        } catch (e: Exception) {
            emit(CKResult.Error(e))
        }
    }

}