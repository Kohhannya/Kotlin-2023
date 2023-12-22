package api.model

import kotlinx.serialization.Serializable

@Serializable
data class AddUserRequest(val login: String,
                          val password: String,
                          val name: String)