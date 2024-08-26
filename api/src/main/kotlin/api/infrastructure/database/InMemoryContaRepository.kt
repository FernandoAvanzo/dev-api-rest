package api.infrastructure.database

import api.domain.model.Conta
import api.domain.model.Portador
import api.domain.repositories.ContaRepository

class InMemoryContaRepository :ContaRepository {
    private val contas = mutableSetOf<Conta>()

    override fun create(conta: Conta) {
        contas.add(conta)
    }

    override fun findByCpf(cpf: String): Conta? {
        return contas.find { it.portador.cpf == cpf }
    }
}