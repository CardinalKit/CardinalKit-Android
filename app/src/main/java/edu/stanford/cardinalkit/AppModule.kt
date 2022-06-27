package edu.stanford.cardinalkit

import android.app.Application
import android.content.Context
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.stanford.cardinalkit.data.repositories.AuthRepositoryImpl
import edu.stanford.cardinalkit.data.repositories.SurveyRepositoryImpl
import edu.stanford.cardinalkit.domain.repositories.AuthRepository
import edu.stanford.cardinalkit.domain.repositories.SurveyRepository
import edu.stanford.cardinalkit.domain.use_cases.UploadSurvey
import edu.stanford.cardinalkit.domain.use_cases.UseCases
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideContext(app: Application): Context = app.applicationContext

    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore

    @Provides
    @Named("usersRef")
    fun provideUsersRef(db: FirebaseFirestore) = db.collection("users")

    @Provides
    @Named("surveysRef")
    fun provideSurveysRef(db: FirebaseFirestore) = db.collection("surveys")

    @Provides
    fun provideOneTapClient(context: Context) = Identity.getSignInClient(context)

    @Provides
    @Named("signInRequest")
    fun provideSignInRequest(
        app: Application
    ) = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(app.getString(R.string.web_client_id))
                .setFilterByAuthorizedAccounts(true)
                .build())
        .setAutoSelectEnabled(true)
        .build()

    @Provides
    @Named("signUpRequest")
    fun provideSignUpRequest(
        app: Application
    ) = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(app.getString(R.string.web_client_id))
                .setFilterByAuthorizedAccounts(false)
                .build())
        .build()

    @Provides
    fun provideGoogleSignInOptions(
        app: Application
    ) = GoogleSignInOptions
        .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(app.getString(R.string.web_client_id))
        .requestEmail()
        .build()

    @Provides
    fun provideGoogleSignInClient(
        app: Application,
        options: GoogleSignInOptions
    ) = GoogleSignIn.getClient(app, options)

    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth,
        oneTapClient: SignInClient,
        @Named("signInRequest")
        signInRequest: BeginSignInRequest,
        signInClient: GoogleSignInClient,
        @Named("usersRef")
        usersRef: CollectionReference
    ): AuthRepository = AuthRepositoryImpl(
        auth = auth,
        oneTapClient = oneTapClient,
        signInRequest = signInRequest,
        signInClient = signInClient,
        usersRef = usersRef
    )

    @Provides
    @Named("surveyRepository")
    fun provideSurveyRepository(
        @Named("surveysRef")
        surveysRef: CollectionReference
    ): SurveyRepository = SurveyRepositoryImpl(surveysRef)

    @Provides
    @Named("useCases")
    fun provideUseCases(
        @Named("surveyRepository")
        repository: SurveyRepository
    ) = UseCases(
        uploadSurvey = UploadSurvey(repository)
    )
}