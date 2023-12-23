package repository.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import model.Author
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object AuthorsTable : LongIdTable("authors")  {
    val login : Column<String> = varchar("login", length = 20)
    val name : Column<String> = varchar("name", length = 20)
}