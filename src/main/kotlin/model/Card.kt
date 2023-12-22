package model

import kotlinx.serialization.Serializable;
import java.time.Instant

@Serializable
data class Card(
    private val id: Long,
    private var text: String,
    private val createdAt: String,
    private var changedAt: String,
    private val author: Author
) {
    fun getId(): Long {
        return this.id
    }

    fun getText(): String {
        return this.text
    }

    fun setText(newText: String) {
        this.text = newText
        this.setChangetAt(Instant.now().toString())
    }

    fun getCreatedAt(): String {
        return this.createdAt
    }

    fun getChangedAt(): String {
        return this.changedAt
    }

    private fun setChangetAt(changingTime: String) {
        this.changedAt = changingTime
    }

    fun getAuthor(): Author {
        return this.author
    }
}
