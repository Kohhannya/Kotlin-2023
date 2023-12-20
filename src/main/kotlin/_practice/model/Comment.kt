package _practice.model

import kotlinx.serialization.Serializable

@Serializable
data class Comment(val id: Long,
                   val text: String,
                   val createdAt: String)