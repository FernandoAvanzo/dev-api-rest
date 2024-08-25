package api.di

import api.application.command.handlers.CreatePortadorCommandHandler
import api.domain.repositories.PortadorRepository
import api.infrastructure.database.InMemoryPortadorRepository
import org.koin.dsl.module

val apiModule = module {
    single<PortadorRepository> { InMemoryPortadorRepository() }
    single { CreatePortadorCommandHandler(get()) }
}