package model

import kotlinx.serialization.Serializable

@Serializable
data class Token(var accessToken: String)