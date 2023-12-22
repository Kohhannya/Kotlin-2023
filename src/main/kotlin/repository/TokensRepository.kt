package repository

import model.Token

interface TokensRepository {
    fun createToken(userLogin: String, userName: String): Token
}