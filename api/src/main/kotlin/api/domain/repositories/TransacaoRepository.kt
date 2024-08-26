package api.domain.repositories

import api.domain.model.Transacao

interface TransacaoRepository {
    fun deposit(transacao: Transacao)
    fun withdraw(transacao: Transacao)
}