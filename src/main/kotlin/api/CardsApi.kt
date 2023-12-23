package api

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import api.model.ChangeCardRequest
import api.model.CreateCardRequest
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import model.Author
import model.Card
import repository.CardsRepository
import org.koin.ktor.ext.inject

fun Application.cardsApi() {

    ///
    fun isValidText(text: String): Boolean {
        return text.length <= 500
    }

    fun authorCheck(card: Card, login: String): Boolean {
        return card.getAuthorLogin() == login
    }
    ///

    routing {

        val cardsRepository by inject<CardsRepository>()

        authenticate("auth-jwt") {
            get("/cards/all") {
                val cards = cardsRepository.getAll()
                call.respond(cards)
            }

            get("/cards/get/{id}") {
                //Вытаскиваем id из параметров запроса или возвращаем 0
                val cardId: Long = call.parameters["id"]?.toLong() ?: 0
                val card: Card? = cardsRepository.getById(cardId)

                call.respond(card ?: HttpStatusCode.NotFound)
            }

            get("/cards/getPage/{perPage}/{number}") {
                val perPage: Int = call.parameters["perPage"]?.toInt() ?: 1
                val number: Int = call.parameters["number"]?.toInt() ?: 1
                val cards = cardsRepository.getByPage(perPage, number)

                call.respond(cards)
            }

            put("/cards/create") {
                val principal = call.principal<JWTPrincipal>()
                val author = Author(
                    login = principal!!.payload.getClaim("login").asString(),
                    name = principal.payload.getClaim("name").asString()
                )

                val request = call.receive<CreateCardRequest>()
                val createdCard: Card = cardsRepository.createCard(request.cardText, author)

                if (isValidText(request.cardText)) call.respond(createdCard)
                else call.respond(HttpStatusCode.LengthRequired)
            }

            patch("/cards/change/{id}") {
                //Определяем логин отправителя запроса
                val principal = call.principal<JWTPrincipal>()
                val login: String = principal!!.payload.getClaim("login").asString()

                val request = call.receive<ChangeCardRequest>()
                val cardId: Long = call.parameters["id"]?.toLong() ?: 0

                //Проверяем, что изменения делает именно автор
                var card = cardsRepository.getById(cardId)

                if (card == null || !authorCheck(card, login)) call.respond(
                    HttpStatusCode.Forbidden, "You can`t change this card"
                )
                else {
                    card = cardsRepository.changeCard(cardId, request.newCardText)
                    call.respond(card ?: HttpStatusCode.NotFound)
                }
            }

            delete("/cards/delete/{id}") {
                //Определяем логин отправителя запроса
                val principal = call.principal<JWTPrincipal>()
                val login: String = principal!!.payload.getClaim("login").asString()

                val cardId: Long = call.parameters["id"]?.toLong() ?: 0

                //Проверяем, что удаляет именно автор
                val card = cardsRepository.getById(cardId)

                if (card == null || !authorCheck(card, login)) call.respond(
                    HttpStatusCode.Forbidden, "You can`t delete this card"
                )
                else {
                    val deleted: Boolean = cardsRepository.deleteCard(cardId)
                    call.respond(deleted)
                }
            }
        }
    }
}
