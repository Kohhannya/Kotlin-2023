package _practice

import _practice.repository.CommentsRepository
import _practice.repository.Impl.DefaultCommentsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val commentsModule = module {
    singleOf(::DefaultCommentsRepository) bind CommentsRepository::class
}