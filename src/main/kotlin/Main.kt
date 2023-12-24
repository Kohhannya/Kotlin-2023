import api.authApi

import api.authApi
import api.cardsApi
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.typesafe.config.ConfigFactory
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.ktor.plugin.Koin
import repository.model.CardsTable
import repository.model.UsersTable

//TIP Press <shortcut raw="SHIFT"/> twice to open the Search Everywhere dialog and type <b>show whitespaces</b>,
// then press <shortcut raw="ENTER"/>. You can now see whitespace characters in your code.
fun main() {
    embeddedServer(Netty, port = 8000) {
        routing {
            get("/") {
                call.respondText(
                    "Hello, system!\n This is a blog-server.\n" +
                            "Sign in and create your first publication!"
                )
            }
        }
        configureServer()
        configureDatabase()
        cardsApi()
        authApi()
    }.start(wait = true)

}
fun Application.configureDatabase() {
    val config = ConfigFactory.load("application.conf")
    val url = "jdbc:postgresql://localhost:9000/postgres"
    val username = "mipt_kotlin_backend"
    val password = "simple_password"
    val db = Database.connect(url = url, user = username, password = password)
    db.initSchema()
}

private fun Database.initSchema() {
    transaction(this) {
        SchemaUtils.create(UsersTable)
        SchemaUtils.create(CardsTable)
    }
}

fun Application.configureServer() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
        status(HttpStatusCode.NotFound) { call, status ->
            call.respondText(text = "404: Page Not Found", status = status)
        }
    }

    install(Koin) {
//        slf4jLogger()
        modules(cardsModule, usersModule)
    }

    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }

    val config = ConfigFactory.load("application.conf")

    val secret = config.getString("jwt.secret")
    val issuer = config.getString("jwt.issuer")
    val audience = config.getString("jwt.audience")
    val myRealm = config.getString("jwt.realm")

    install(Authentication) {
        jwt("auth-jwt") {
            realm = myRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(secret))
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .build()
            )

            validate { credential ->
                if (credential.payload.getClaim("username").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }

            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}
