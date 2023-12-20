package org.kohhannya

import org.kohhannya.repository.CommentsRepository
import org.kohhannya.repository.Impl.DefaultCommentsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val commentsModule = module {
    singleOf(::DefaultCommentsRepository) bind CommentsRepository::class
}