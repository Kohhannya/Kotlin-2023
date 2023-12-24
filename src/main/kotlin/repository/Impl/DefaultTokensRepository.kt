package repository.Impl

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.typesafe.config.ConfigFactory
import model.Token
import repository.TokensRepository
import java.util.*

class DefaultTokensRepository : TokensRepository {

    private val config = ConfigFactory.load("application.conf")
    private val secret = config.getString("jwt.secret")
    private val issuer = config.getString("jwt.issuer")
    private val audience = config.getString("jwt.audience")

    override fun createToken(userLogin: String, userName: String): Token {
        val token: String = JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("login", userLogin)
            .withClaim("name", userName)
            .withExpiresAt(Date(System.currentTimeMillis() + 600000))
            .sign(Algorithm.HMAC256(secret))

        return Token(token)
    }
}