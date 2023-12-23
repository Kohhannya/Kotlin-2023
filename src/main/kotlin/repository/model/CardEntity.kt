package repository.model

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class CardEntity(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<CardEntity>(CardsTable)

    var text by CardsTable.text
    var createdAt by CardsTable.createdAt
    var changedAt by CardsTable.changedAt
    var authorLogin by CardsTable.authorLogin
}