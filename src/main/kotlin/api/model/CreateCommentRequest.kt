package org.kohhannya.api.model

import kotlinx.serialization.Serializable
import org.kohhannya.model.Comment

@Serializable
data class CreateCommentRequest(
    val commentText: String
) {
}