package edu.stanford.cardinalkit.di

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
import edu.stanford.cardinalkit.R
import edu.stanford.cardinalkit.common.Constants
import edu.stanford.cardinalkit.data.repositories.*
import edu.stanford.cardinalkit.domain.repositories.*
import edu.stanford.cardinalkit.domain.use_cases.auth.*
import edu.stanford.cardinalkit.domain.use_cases.tasks.GetTasks
import edu.stanford.cardinalkit.domain.use_cases.tasks.TasksUseCases
import edu.stanford.cardinalkit.domain.use_cases.contacts.ContactsUseCases
import edu.stanford.cardinalkit.domain.use_cases.contacts.GetContacts
import edu.stanford.cardinalkit.domain.use_cases.surveys.GetSurvey
import edu.stanford.cardinalkit.domain.use_cases.surveys.SurveysUseCases
import edu.stanford.cardinalkit.domain.use_cases.surveys.UploadSurveyResult
import edu.stanford.cardinalkit.domain.use_cases.tasklogs.GetTaskLogs
import edu.stanford.cardinalkit.domain.use_cases.tasklogs.TaskLogUseCases
import edu.stanford.cardinalkit.domain.use_cases.tasklogs.UploadTaskLog
import edu.stanford.cardinalkit.services.HealthConnectManager
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
    @Named(Constants.USERS_REF)
    fun provideUsersRef(
        db: FirebaseFirestore
    ): CollectionReference {
        return db.collection(
            "${Constants.FIRESTORE_BASE_DOCUMENT}/${Constants.FIRESTORE_USERS_COLLECTION}"
        )
    }

    @Provides
    @Named(Constants.TASKS_REF)
    fun provideTasksRef(
        db: FirebaseFirestore
    ): CollectionReference? {
        return db.collection(
            "${Constants.FIRESTORE_BASE_DOCUMENT}/${Constants.FIRESTORE_TASKS_COLLECTION}"
        )
    }

    @Provides
    @Named(Constants.TASKLOG_REF)
    fun provideTaskLogRef(
        db: FirebaseFirestore
    ): CollectionReference? {
        val user = Firebase.auth.currentUser
        user?.let {
            return db.collection(
                "${Constants.FIRESTORE_BASE_DOCUMENT}/${Constants.FIRESTORE_USERS_COLLECTION}/${user.uid}/${Constants.FIRESTORE_TASKLOG_COLLECTION}"
            )
        }
        return null
    }

    @Provides
    @Named(Constants.SURVEYS_REF)
    fun provideSurveysRef(
        db: FirebaseFirestore
    ): CollectionReference? {
        val user = Firebase.auth.currentUser
        user?.let {
            return db.collection(
                "${Constants.FIRESTORE_BASE_DOCUMENT}/${Constants.FIRESTORE_USERS_COLLECTION}/${user.uid}/${Constants.FIRESTORE_SURVEYS_COLLECTION}"
            )
        }
        return null
    }

    @Provides
    @Named(Constants.CONTACTS_REF)
    fun provideContactsRef(
        db: FirebaseFirestore
    ): CollectionReference? {
        val user = Firebase.auth.currentUser
        user?.let {
            return db.collection(
                "${Constants.FIRESTORE_BASE_DOCUMENT}/${Constants.FIRESTORE_CONTACTS_COLLECTION}"
            )
        }
        return null
    }

    @Provides
    fun provideOneTapClient(context: Context) = Identity.getSignInClient(context)

    @Provides
    @Named(Constants.SIGN_IN_REQUEST)
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
    @Named(Constants.SIGN_UP_REQUEST)
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
        @Named(Constants.SIGN_IN_REQUEST)
        signInRequest: BeginSignInRequest,
        signInClient: GoogleSignInClient,
        @Named(Constants.USERS_REF)
        usersRef: CollectionReference
    ): AuthRepository = AuthRepositoryImpl(
        auth = auth,
        oneTapClient = oneTapClient,
        signInRequest = signInRequest,
        signInClient = signInClient,
        usersRef = usersRef
    )

    @Provides
    @Named(Constants.SURVEY_REPOSITORY)
    fun provideSurveyRepository(
        @Named(Constants.SURVEYS_REF)
        surveysRef: CollectionReference?,
        context: Context
    ): SurveyRepository = SurveyRepositoryImpl(surveysRef, context)

    @Provides
    @Named(Constants.TASKS_REPOSITORY)
    fun provideTasksRepository(
        @Named(Constants.TASKS_REF)
        tasksRef: CollectionReference?,
        context: Context
    ): TasksRepository = TasksRepositoryImpl(tasksRef, context)

    @Provides
    @Named(Constants.TASKLOG_REPOSITORY)
    fun provideTaskLogRepository(
        @Named(Constants.TASKLOG_REF)
        taskLogRef: CollectionReference?
    ): TaskLogRepository = TaskLogRepositoryImpl(taskLogRef)

    @Provides
    @Named(Constants.CONTACTS_REPOSITORY)
    fun provideContactsRepository(
        @Named(Constants.CONTACTS_REF)
        contactsRef: CollectionReference?,
        context: Context
    ): ContactsRepository = ContactsRepositoryImpl(contactsRef, context)

    @Provides
    @Named(Constants.SURVEYS_USE_CASES)
    fun provideSurveysUseCases(
        @Named(Constants.SURVEY_REPOSITORY)
        surveyRepository: SurveyRepository,
    ) = SurveysUseCases(
        uploadSurveyResult = UploadSurveyResult(surveyRepository),
        getSurvey = GetSurvey(surveyRepository)
    )

    @Provides
    @Named(Constants.CONTACTS_USE_CASES)
    fun provideContactsUseCases(
        @Named(Constants.CONTACTS_REPOSITORY)
        contactsRepository: ContactsRepository
    ) = ContactsUseCases(
        getContacts = GetContacts(contactsRepository)
    )

    @Provides
    @Named(Constants.TASKS_USE_CASES)
    fun provideTasksUseCases(
        @Named(Constants.TASKS_REPOSITORY)
        repository: TasksRepository,
    ) = TasksUseCases(
        getTasks = GetTasks(repository)
    )

    @Provides
    @Named(Constants.TASKLOG_USE_CASES)
    fun provideTaskLogUseCases(
        @Named(Constants.TASKLOG_REPOSITORY)
        repository: TaskLogRepository
    ) = TaskLogUseCases(
        uploadTaskLog = UploadTaskLog(repository),
        getTaskLogs = GetTaskLogs(repository)
    )

    @Provides
    @Named(Constants.AUTH_USE_CASES)
    fun provideAuthUseCases(
        repository: AuthRepository
    ) = AuthUseCases(
        signInWithEmail = SignInWithEmail(repository),
        signUpWithEmail = SignUpWithEmail(repository),
        oneTapSignIn = OneTapSignIn(repository),
        signInWithGoogle = SignInWithGoogle(repository),
        saveUser = SaveUser(repository),
        updateLastActive = UpdateLastActive(repository),
        getAuthStatus = GetAuthStatus(repository),
        resetPassword = ResetPassword(repository)
    )

    @Provides
    fun provideHealthConnectManager(
        context: Context
    ) = HealthConnectManager(context)
}