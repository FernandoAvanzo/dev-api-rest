package api.di

import api.application.command.handlers.*
import api.application.query.handlers.GetContaQueryHandler
import api.domain.repositories.ContaRepository
import api.domain.repositories.TransacaoRepository
import api.domain.repositories.PortadorRepository
import api.infrastructure.database.InMemoryContaRepository
import api.infrastructure.database.InMemoryTransacaoRepository
import api.infrastructure.database.InMemoryPortadorRepository
import org.koin.dsl.module

val apiModule = module {
    single<PortadorRepository> { InMemoryPortadorRepository() }
    single<ContaRepository> { InMemoryContaRepository() }
    single<TransacaoRepository> { InMemoryTransacaoRepository() }
    single { CreatePortadorCommandHandler(get()) }
    single { CreateContaCommandHandler(get(),get()) }
    single { GetContaQueryHandler(get()) }
    single { BlockContaCommandHandler(get(),get()) }
    single { UnblockContaCommandHandler(get(),get()) }
    single { CreateDepositoCommandHandler(get(),get()) }
    single { CreateSaqueCommandHandler(get(),get()) }
}