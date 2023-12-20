package HW3.repository

import HW3.model.Card


interface CardsRepository {
    fun getAll(): Collection<Card>

    fun getById(id: Long): Card?

    fun createCard(cardText: String): Card

    fun changeCard(id: Long, newCardText: String): Card?

    fun deleteCard(id: Long): Boolean
}