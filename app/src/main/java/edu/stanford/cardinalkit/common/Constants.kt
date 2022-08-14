package edu.stanford.cardinalkit.common

object Constants {



    // General
    const val APP_PACKAGE_NAME = "edu.stanford.cardinalkit"
    const val TAG = "CardinalKit"

    // Firebase paths
    const val FIRESTORE_BASE_DOCUMENT = "/studies/${APP_PACKAGE_NAME}"
    const val FIRESTORE_USERS_COLLECTION = "users"
    const val FIRESTORE_SURVEYS_COLLECTION = "surveys"
    const val FIRESTORE_TASKS_COLLECTION = "tasks"
    const val FIRESTORE_TASKLOG_COLLECTION = "tasklogs"

    // Screens
    const val MAIN_SCREEN = "Main"
    const val HOME_SCREEN = "Home"
    const val TASKS_SCREEN = "Tasks"
    const val PROFILE_SCREEN = "Profile"
    const val CONTACTS_SCREEN = "Contacts"
    const val LOGIN_SCREEN = "Login"
    const val ONBOARDING_SCREEN = "Onboarding"
    const val WELCOME_SCREEN = "Welcome"
    const val REVIEW_SCREEN = "Review"
    const val REGISTER_SCREEN = "Register"
    const val REVIEW_CONSENT="Review Consent"
    const val SIGN_IN_METHOD = "Sign In"
    const val JOIN_STUDY_SCREEN="Join Study"
    const val FORGOT_PASSWORD_SCREEN= "Forgot Password"


    // Named dependencies to be injected
    const val USERS_REF = "usersRef"
    const val SURVEYS_REF = "surveysRef"
    const val TASKS_REF = "tasksRef"
    const val TASKLOG_REF = "taskLogRef"

    const val SIGN_IN_REQUEST = "signInRequest"
    const val SIGN_UP_REQUEST = "signUpRequest"

    const val SURVEY_REPOSITORY = "surveyRepository"
    const val SURVEYS_USE_CASES = "surveysUseCases"

    const val TASKS_REPOSITORY = "tasksRepository"
    const val TASKS_USE_CASES = "tasksUseCases"

    const val TASKLOG_REPOSITORY = "taskLogRepository"
    const val TASKLOG_USE_CASES = "taskLogUseCases"

    const val CONTACTS_REPOSITORY = "contactsRepository"
    const val CONTACTS_USE_CASES = "contactsUseCases"

    const val AUTH_REPOSITORY = "authRepository"
    const val AUTH_USE_CASES = "authUseCases"

    // Intents
    const val SURVEY_NAME = "edu.stanford.cardinalkit.SURVEY_NAME"
    const val TASK_ID = "edu.stanford.cardinalkit.TASK_ID"

}