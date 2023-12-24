package repository.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object UsersTable : LongIdTable("users") {
    val login : Column<String> = varchar("login", length = 20)

    val password:  Column<String> = varchar("password", length = 20)
    val name:  Column<String> = varchar("name", length = 20)

    val createdAt: Column<Instant> = timestamp("createdAt").clientDefault { Clock.System.now() }
}