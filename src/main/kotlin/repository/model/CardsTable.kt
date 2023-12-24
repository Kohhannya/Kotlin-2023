package repository.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object CardsTable : LongIdTable("cards") {
    val text: Column<String> = varchar("text", length = 1024)

    val createdAt: Column<Instant> = timestamp("createdAt").clientDefault { Clock.System.now() }
    val changedAt: Column<Instant> = timestamp("changedAt").clientDefault { Clock.System.now() }

    val authorLogin: Column<String> = varchar("authorLogin", length = 20)
}