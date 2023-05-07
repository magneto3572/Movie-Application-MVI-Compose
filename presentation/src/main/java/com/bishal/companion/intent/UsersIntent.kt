package com.bishal.companion.intent

sealed class UsersIntent {
    object GetUserData : UsersIntent()
    data class ItemClicked(val uri: String) : UsersIntent()
}
