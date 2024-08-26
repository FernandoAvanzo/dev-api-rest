package api.infrastructure.database

import api.domain.model.Transacao
import api.domain.repositories.ExtratoRepository

class InMemoryExtratoRepository : ExtratoRepository {
    private val transacaoRegistry = mutableSetOf<Transacao>()

    override fun deposit(transacao: Transacao){
        transacaoRegistry.add(transacao)
    }

    override fun withdraw(transacao: Transacao) {
        transacaoRegistry.add(transacao)
    }
}