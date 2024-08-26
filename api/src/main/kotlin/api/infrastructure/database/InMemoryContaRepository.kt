package api.infrastructure.database

import api.domain.model.Conta
import api.domain.repositories.ContaRepository

class InMemoryContaRepository :ContaRepository {
    private val contas = mutableSetOf<Conta>()

    override fun create(conta: Conta) {
        contas.add(conta)
    }
}