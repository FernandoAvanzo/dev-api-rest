package api.infrastructure.database

import api.domain.model.Conta
import api.domain.model.Extrato
import api.domain.repositories.ExtratoRepository

class InMemoryExtratoRepository : ExtratoRepository {
    private val extratoRegistry = mutableSetOf<Extrato>()

    override fun deposit(extrato: Extrato){
        extratoRegistry.add(extrato)
    }

    override fun withdraw(extrato: Extrato) {
        extratoRegistry.add(extrato)
    }
}