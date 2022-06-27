package com.example.vocabularytrainer.util

object Constants {
    //WELCOME SCREEN CONSTANCE
    const val GIT_HUB_LINK = "https://github.com/MarkD1916"
    const val WEB_BLOG_LINK = "https://vmakd1916vocabularytrainer.herokuapp.com/"

    //REGISTRATION SCREEN CONSTANCE
    const val PASSWORD_LENGTH = 9
    const val PASSWORD_REQUIRE = "Your password must contain at least 9 characters"

    //AUTH SHARED PREFERENCE CONSTANTS
    const val ENCRYPTED_SHARED_PREF_NAME = "enc_shared_pref"
    const val KEY_LOGGED_IN_EMAIL = "KEY_LOGGED_IN_EMAIL"
    const val KEY_PASSWORD = "KEY_PASSWORD"
    const val KEY_TOKEN = "KEY_TOKEN"
    const val KEY_USER_ID = "KEY_USER_ID"
    const val KEY_ALL_GROUP_ID = "KEY_ALL_GROUP_ID"

    //INTERCEPTOR CONSTANTS
    val IGNORE_AUTH_URL = listOf("/mobile/users/", "/mobile/token/login")
    const val TOKEN_HEADER_NAME = "Authorization"

    fun getTokenValue(token: String) = "Token $token"
    fun getCountryFlagUrl(countryShortName: String) = "https://countryflagsapi.com/svg/$countryShortName"

    const val MAIN_GROUP_NAME = "All"

}