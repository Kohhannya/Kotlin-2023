package repository.Impl

import kotlinx.datetime.Clock
import repository.model.UserEntity
import model.User
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import repository.UsersRepository
import repository.model.CardEntity
import repository.model.UsersTable
import kotlin.random.Random

class DBUsersRepository : UsersRepository {

    private fun UserEntity.toUser(): User {
        return User(
            login = login,
            password = password,
            name = name,
            createdAt = createdAt.toString()
        )
    }

    override fun addUser(login: String, password: String, name: String): Boolean {
        return transaction {
            val userId: Long = Random.nextLong()
            val oldUser = UserEntity.find { UsersTable.login eq login }
            if (oldUser.empty()) false
            else {
                UserEntity.new(userId) {
                    this.login = login
                    this.password = password
                    this.name = name
                    this.createdAt = Clock.System.now()
                }
                true
            }
        }
    }

    override fun getByLogin(login: String): User? {
        return transaction {
            val user = UserEntity.find { UsersTable.login eq login }
            if (user.empty()) null
            else user.first().toUser()
        }
    }
}