package api.infrastructure.database

import api.domain.model.Transacao
import api.domain.repositories.TransacaoRepository

class InMemoryTransacaoRepository : TransacaoRepository {
    private val transacaoRegistry = mutableSetOf<Transacao>()

    override fun deposit(transacao: Transacao){
        transacaoRegistry.add(transacao)
    }

    override fun withdraw(transacao: Transacao) {
        transacaoRegistry.add(transacao)
    }
}