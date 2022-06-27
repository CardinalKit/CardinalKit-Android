package edu.stanford.cardinalkit.common

object Constants {

    // General
    const val APP_PACKAGE_NAME = "edu.stanford.cardinalkit"

    // Firebase paths
    const val FIRESTORE_BASE_DOCUMENT = "/studies/${APP_PACKAGE_NAME}"
    const val FIRESTORE_USERS_COLLECTION = "users"
    const val FIRESTORE_SURVEYS_COLLECTION = "surveys"

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

    // Named dependencies to be injected
    const val USERS_REF = "usersRef"
    const val SURVEYS_REF = "surveysRef"
    const val SIGN_IN_REQUEST = "signInRequest"
    const val SIGN_UP_REQUEST = "signUpRequest"
    const val SURVEY_REPOSITORY = "surveyRepository"
    const val USE_CASES = "useCases"

    // Intents
    const val SURVEY_NAME = "edu.stanford.cardinalkit.SURVEY_NAME"

}