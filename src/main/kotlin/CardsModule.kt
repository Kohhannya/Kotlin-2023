import repository.CardsRepository
import repository.Impl.DBCardsRepository
import repository.Impl.DefaultCardsRepository

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val cardsModule = module {
    singleOf(::DefaultCardsRepository) bind CardsRepository::class
//    singleOf(::DBCardsRepository) bind CardsRepository::class
}