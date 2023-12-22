import repository.UsersRepository
import repository.TokensRepository
import repository.Impl.DefaultUsersRepository
import repository.Impl.DefaultTokensRepository

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val usersModule = module {
    singleOf(::DefaultUsersRepository) bind UsersRepository::class
    singleOf(::DefaultTokensRepository) bind TokensRepository::class
}