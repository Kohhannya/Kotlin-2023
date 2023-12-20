package HW3.api.model

import kotlinx.serialization.Serializable

@Serializable
data class ChangeCardRequest(
    val newCardText: String
) {
}