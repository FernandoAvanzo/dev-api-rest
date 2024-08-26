package api.domain.repositories

import api.domain.model.Transacao

interface ExtratoRepository {
    fun deposit(transacao: Transacao)
    fun withdraw(transacao: Transacao)
}