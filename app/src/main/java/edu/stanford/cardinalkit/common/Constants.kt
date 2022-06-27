package edu.stanford.cardinalkit.common

object Constants {

    const val SURVEY_NAME = "edu.stanford.cardinalkit.SURVEY_NAME"

    const val FIRESTORE_USERS_COLLECTION = "/studies/edu.stanford.cardinalkit/users"

    // Screens
    const val MAIN_SCREEN = "Main"
    const val HOME_SCREEN = "Home"
    const val TASKS_SCREEN = "Tasks"
    const val PROFILE_SCREEN = "Profile"
    const val CONTACTS_SCREEN = "Contacts"
    const val LOGIN_SCREEN = "Login"

    // Named dependencies to be injected
    const val USERS_REF = "usersRef"
    const val SURVEYS_REF = "surveysRef"
    const val SIGN_IN_REQUEST = "signInRequest"
    const val SIGN_UP_REQUEST = "signUpRequest"
    const val SURVEY_REPOSITORY = "surveyRepository"
    const val USE_CASES = "useCases"

}