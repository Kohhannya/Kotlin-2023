package api

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import api.model.ChangeCardRequest
import api.model.CreateCardRequest
import model.Card
import repository.CardsRepository
import org.koin.ktor.ext.inject

fun Application.cardsApi() {
    routing {

        val cardsRepository by inject<CardsRepository>()

        get("/cards/all") {
//            println("Выводим полный список публикаций")

            val cards = cardsRepository.getAll()
            call.respond(cards)
        }
        get("/cards/get/{id}") {
//            println("Находим публикацию по id")

            //Вытаскиваем id из параметров запроса или возвращаем 0
            val cardId: Long = call.parameters["id"]?.toLong() ?: 0
            val card: Card? = cardsRepository.getById(cardId)

            call.respond(card ?: HttpStatusCode.NotFound)
        }
        get("/cards/getPage/{perPage}/{number}") {
//            println("Находим публикацию по id")

            //Вытаскиваем размер страницы и её номер из параметров запроса
            val perPage: Int = call.parameters["perPage"]?.toInt() ?: 1
            val number: Int = call.parameters["number"]?.toInt() ?: 1
            val cards = cardsRepository.getByPage(perPage, number)

            call.respond(cards)
        }

        put("/cards/create") {
//            println("Создаём публикацию")

            val request = call.receive<CreateCardRequest>()
            val createdCard: Card = cardsRepository.createCard(request.cardText)

            if (isValidText(request.cardText)) call.respond(createdCard)
            else call.respond(HttpStatusCode.LengthRequired)
        }
        patch("/cards/change/{id}") {
//            println("Меняем публикацию по id")

            val request = call.receive<ChangeCardRequest>()
            val cardId: Long = call.parameters["id"]?.toLong() ?: 0
            val card: Card? = cardsRepository.changeCard(cardId, request.newCardText)

            call.respond(card ?: HttpStatusCode.NotFound)
        }
        delete("/cards/delete/{id}") {
//            println("Удаляем публикацию по id")

            val cardId: Long = call.parameters["id"]?.toLong() ?: 0
            val deleted: Boolean = cardsRepository.deleteCard(cardId)

            call.respond(deleted)
        }
    }
}

fun isValidText(text: String): Boolean {
    return text.length <= 500
}
