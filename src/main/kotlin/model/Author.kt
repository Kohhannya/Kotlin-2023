package model

import kotlinx.serialization.Serializable

@Serializable
data class Author(
    private val login: String,
    private val name: String
) {
    fun getLogin(): String {
        return this.login
    }

    fun getName(): String {
        return this.name
    }
}