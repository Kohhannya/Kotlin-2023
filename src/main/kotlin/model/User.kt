package model

import kotlinx.serialization.Serializable

@Serializable
data class User(private val login: String,
                private val password: String,
                private val name: String,
                private val createdAt: String) {
    fun getLogin(): String {
        return this.login
    }
    fun getPassword(): String {
        return this.password
    }
    fun getName(): String {
        return this.name
    }
    fun getCreatedAt(): String {
        return this.createdAt
    }
}