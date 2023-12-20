package HW3.api

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import HW3.api.model.ChangeCardRequest
import HW3.api.model.CreateCardRequest
import HW3.repository.CardsRepository
import org.koin.ktor.ext.inject

fun Application.cardsApi() {
    routing {

        val cardsRepository by inject<CardsRepository>()

        get("/cards/all") {
            println("Выводим полный список публикаций")

            val cards = cardsRepository.getAll()
            call.respond(cards)
        }
        get("/cards/{id}") {
            println("Находим публикацию по id")

            val cardId = call.parameters["id"]?.toLong() ?: 0
            val card = cardsRepository.getById(cardId)

            call.respond(card ?: HttpStatusCode.NotFound) }

        post("/comments/create") {
            println("Создаём публикацию")

            val request = call.receive<CreateCardRequest>()
            val createdCard = cardsRepository.createCard(request.cardText)

            call.respond(createdCard)
        }
        post("/comments/change/{id}") {
            println("Меняем публикацию по id")

            val request = call.receive<ChangeCardRequest>()
            val createdCard = cardsRepository.createCard(request.newCardText)

            call.respond(createdCard)
        }
    }
}
