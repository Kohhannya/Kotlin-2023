import repository.CardsRepository
import repository.Impl.DBCardsRepository

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val cardsModule = module {
    singleOf(::DBCardsRepository) bind CardsRepository::class
}