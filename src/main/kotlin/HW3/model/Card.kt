package HW3.model

import kotlinx.serialization.Serializable;

@Serializable
data class Card(val id: Long,
                var text: String,
                val createdAt: String,
                var changedAt: String)
