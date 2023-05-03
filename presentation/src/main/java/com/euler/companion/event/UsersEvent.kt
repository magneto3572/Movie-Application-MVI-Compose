package com.euler.companion.event

sealed class UsersEvent {
    data class OpenWebBrowserWithDetails(val uri: String) : UsersEvent()
}
