package api.di

import api.application.command.handlers.CreateContaCommandHandler
import api.application.command.handlers.CreatePortadorCommandHandler
import api.application.query.handlers.GetContaQueryHandler
import api.domain.repositories.ContaRepository
import api.domain.repositories.PortadorRepository
import api.infrastructure.database.InMemoryContaRepository
import api.infrastructure.database.InMemoryPortadorRepository
import org.koin.dsl.module

val apiModule = module {
    single<PortadorRepository> { InMemoryPortadorRepository() }
    single<ContaRepository> { InMemoryContaRepository() }
    single { CreatePortadorCommandHandler(get()) }
    single { CreateContaCommandHandler(get(),get()) }
    single { GetContaQueryHandler(get()) }
}