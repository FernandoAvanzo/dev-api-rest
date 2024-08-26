package api.infrastructure.database

import api.domain.model.Conta
import api.domain.repositories.ContaRepository

class InMemoryContaRepository :ContaRepository {
    override fun create(conta: Conta) {
        TODO("Not yet implemented")
    }
}