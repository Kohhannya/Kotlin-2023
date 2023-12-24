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
            login = this.login,
            password = this.password,
            name = this.name,
            createdAt = this.createdAt.toString()
        )
    }

    override fun addUser(login: String, password: String, name: String): Boolean {
        return transaction {
            val userId: Long = Random.nextLong()
            val num = UserEntity.find { UsersTable.login eq login }.count()
            println(num)
            if (num > 0) false
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
            println(user + " " + user.count())
            if (user.count() > 1) null
            else user.first().toUser()
        }
    }
}