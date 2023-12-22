package repository

import model.Author
import model.Card


interface CardsRepository {

    //Получение списка всех созданных публикаций
    fun getAll(): Collection<Card>

    //Получение публикации по id
    fun getById(id: Long): Card?

    //Получение публикаций со страницы num (по perPage публикаций на странице)
    fun getByPage(perPage: Int, num: Int): Collection<Card>

    //Создание публикации с текстом cardText
    fun createCard(cardText: String, author: Author): Card

    //Изменение текста публикации по id на newCardText
    fun changeCard(id: Long, newCardText: String): Card?

    //Опция удаления публикации из списка по id
    fun deleteCard(id: Long): Boolean
}