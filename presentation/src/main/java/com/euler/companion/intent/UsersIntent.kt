package com.euler.companion.intent

sealed class UsersIntent {
    object GetUserData : UsersIntent()
    data class ItemClicked(val uri: String) : UsersIntent()
}
