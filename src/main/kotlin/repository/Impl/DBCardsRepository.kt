package repository.Impl

import kotlinx.datetime.Clock
import model.Author
import model.Card
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import repository.CardsRepository
import repository.model.CardEntity
import repository.model.CardsTable
import kotlin.random.Random

class DBCardsRepository : CardsRepository {
    companion object {
        private const val MAX_CARDS_COUNT = 100
    }

    private fun CardEntity.toCard(): Card {
        return Card(
            id = id.value,
            text = text,
            createdAt = createdAt.toString(),
            changedAt = createdAt.toString(),
            authorLogin = authorLogin
        )
    }


    override fun getAll(): Collection<Card> {
        return transaction {
            val query = CardsTable.selectAll().limit(MAX_CARDS_COUNT)
            val entities = CardEntity.wrapRows(query)
            entities.map { it.toCard() }
        }
    }

    override fun getById(id: Long): Card? {
        return transaction { CardEntity.findById(id)?.toCard() }
    }

    override fun getByPage(perPage: Int, num: Int): Collection<Card> {
        return transaction {
            val query = CardsTable.selectAll().limit(MAX_CARDS_COUNT)
            val entities = CardEntity.wrapRows(query)
            val first = perPage * (num - 1)

            if (perPage < 1 || num < 1 || entities.count() <= num) emptyList<Card>()
            else entities.map { it.toCard() }.subList(first, first + perPage)
        }
    }

    override fun createCard(cardText: String, author: Author): Card {
        return transaction {
            val cardId: Long = Random.nextLong()
            val createdEntity = CardEntity.new(cardId) {
                this.text = cardText
                this.createdAt = Clock.System.now()
                this.changedAt = Clock.System.now()
                this.authorLogin = author.getLogin()
            }
            createdEntity.toCard()
        }
    }

    override fun changeCard(id: Long, newCardText: String): Card? {
        return transaction {
            val card = CardEntity.findById(id)

            card?.text= newCardText
            card?.changedAt = Clock.System.now()
            card?.toCard()
        }
    }

    override fun deleteCard(id: Long): Boolean {
        return transaction {
            val card = CardEntity.findById(id)
            var deleted: Boolean = false
            if (card != null) {
                card.delete()
                deleted = true
            }
            deleted
        }
    }
}