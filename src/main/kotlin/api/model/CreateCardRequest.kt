package api.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateCardRequest(
    val cardText: String
) {
}