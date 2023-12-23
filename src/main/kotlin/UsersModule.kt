import repository.UsersRepository
import repository.TokensRepository
import repository.Impl.DefaultTokensRepository

import repository.Impl.DBUsersRepository
import repository.Impl.DefaultUsersRepository

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val usersModule = module {
//    singleOf(::DBUsersRepository) bind UsersRepository::class
    singleOf(::DefaultUsersRepository) bind UsersRepository::class

    singleOf(::DefaultTokensRepository) bind TokensRepository::class
}