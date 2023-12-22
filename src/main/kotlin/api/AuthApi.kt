package api

import api.model.AddUserRequest
import api.model.LoginRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import repository.TokensRepository
import repository.UsersRepository

fun Application.authApi() {
    routing {

        val usersRepository by inject<UsersRepository>()
        val tokensRepository by inject<TokensRepository>()

        put("/register") {
            val request = call.receive<AddUserRequest>()
            println("Пробуем зарегистрировать пользователя ${request.login}")

            val added: Boolean = usersRepository.addUser(
                request.login,
                request.password,
                request.name)
            if (added) call.respond(HttpStatusCode.OK)
            else call.respond(HttpStatusCode.Conflict,
                "User with login '${request.login}' has already registered")
        }

        post("/login") {
            val request = call.receive<LoginRequest>()
            println("Запрос на вход в систему от пользователя c логином ${request.login}")

            val user = usersRepository.getByLogin(request.login)
            // Check username and password
            // ...
            if (user != null && user.getPassword() == request.password) {
                val token = tokensRepository.createToken(user.getLogin(), user.getName())

                println("Получение токена доступа")
                //Возвращаем JWT токен
                call.response.header(HttpHeaders.Authorization, token.accessToken)
                call.respond(hashMapOf("token" to token.accessToken))
            } else
                call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
        }

        authenticate("auth-jwt") {
            get("/hello") {
                println("Прошли авторизацию")

                val principal = call.principal<JWTPrincipal>()
                val name = principal!!.payload.getClaim("name").asString()
                val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
                call.respondText("Hello, $name! Token is expired at ${expiresAt?.div(60000)} min.")
            }
        }
    }
}