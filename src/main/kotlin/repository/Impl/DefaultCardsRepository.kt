package repository.Impl

import model.Author
import model.Card
import repository.CardsRepository
import java.time.Instant
import kotlin.math.min
import kotlin.random.Random

class DefaultCardsRepository : CardsRepository {

    //Неизменяемое множество публикаций
    private val cards: MutableSet<Card> = mutableSetOf()

    override fun getAll(): Collection<Card> {
        return cards.toList()
    }

    override fun getById(id: Long): Card? {
        return cards.find { it.getId() == id }
    }

    override fun getByPage(perPage: Int, num: Int): Collection<Card> {

        if (perPage < 1 || num < 1 || cards.size <= num) return emptyList()

        val first: Int = perPage * (num - 1)
        return cards.toList().subList(first, min(cards.size, first + perPage))
    }

    override fun createCard(cardText: String, author: Author): Card {
        val time: String = Instant.now().toString()
        val createdCard = Card(
            id = Random.nextLong(),
            text = cardText,
            createdAt = time,
            changedAt = time,
            authorLogin = author.getLogin()
        )
        cards.add(createdCard)
        return createdCard
    }

    override fun changeCard(id: Long, newCardText: String): Card? {
        val card = cards.find { it.getId() == id }

        card?.setText(newCardText)
        return card
    }

    override fun deleteCard(id: Long): Boolean {
        val card = cards.find { it.getId() == id }
        if (card == null) return false

        return cards.removeIf { it.getId() == id }
    }
}