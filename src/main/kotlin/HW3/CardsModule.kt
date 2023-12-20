package org.kohhannya.HW3

import HW3.repository.CardsRepository
import HW3.repository.Impl.DefaultCardsRepository

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val cardsModule = module {
    singleOf(::DefaultCardsRepository) bind CardsRepository::class
}