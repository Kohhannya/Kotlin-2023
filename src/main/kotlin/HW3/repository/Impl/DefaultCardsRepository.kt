package HW3.repository.Impl

import HW3.model.Card
import HW3.repository.CardsRepository
import java.time.Instant
import kotlin.random.Random

class DefaultCardsRepository : CardsRepository {

    private val cards: MutableSet<Card> = mutableSetOf()

    override fun getAll(): Collection<Card> {
        return cards.toList()
    }

    override fun getById(id: Long): Card? {
        return cards.find { it.id == id }
    }

    override fun createCard(cardText: String): Card {
        val time = Instant.now().toString()
        val createdCard = Card(
            id = Random.nextLong(),
            text = cardText,
            createdAt = time,
            changedAt = time
        )
        cards.add(createdCard)
        return createdCard
    }

    override fun changeCard(id: Long, newCardText: String): Card? {
        val card = cards.find { it.id == id }

        if (card != null) {
            card.text = newCardText
            card.changedAt = Instant.now().toString()
        }
        return card
    }

    override fun deleteCard(id: Long): Boolean {
        val card = cards.find { it.id == id }
        if (card == null) return false

        cards.remove(card)
        return true
    }
}