package api.domain.repositories

import api.domain.model.Extrato

interface ExtratoRepository {
    fun deposit(extrato: Extrato)
    fun withdraw(extrato: Extrato)
}