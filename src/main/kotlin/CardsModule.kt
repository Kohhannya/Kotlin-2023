import repository.CardsRepository
import repository.Impl.DefaultCardsRepository

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val cardsModule = module {
    singleOf(::DefaultCardsRepository) bind CardsRepository::class
}