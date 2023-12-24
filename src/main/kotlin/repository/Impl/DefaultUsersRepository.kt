package repository.Impl

import model.User
import repository.UsersRepository
import java.time.Instant

class DefaultUsersRepository : UsersRepository {

    private val users: MutableSet<User> = mutableSetOf()

    override fun addUser(login: String, password: String, name: String): Boolean {
        println("Добавляем нового пользователя в систему")

        val time: String = Instant.now().toString()
        val newUser = User(
            login = login,
            password = password,
            name = name,
            createdAt = time
        )

        if (getByLogin(login) != null) return false
        return users.add(newUser)
    }

    override fun getByLogin(login: String): User? {
        return users.find { it.getLogin() == login }
    }
}