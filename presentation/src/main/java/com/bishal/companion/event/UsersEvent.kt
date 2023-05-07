package com.bishal.companion.event

sealed class UsersEvent {
    data class OpenWebBrowserWithDetails(val uri: String) : UsersEvent()
}
