package api.infrastructure.database

import api.domain.model.Conta
import api.domain.model.Portador
import api.domain.repositories.ContaRepository

class InMemoryContaRepository :ContaRepository {
    private val contas = mutableSetOf<Conta>()

    override fun create(conta: Conta) {
        contas.add(conta)
    }

    override fun findByPortador(portador: Portador): Conta? {
        return contas.find { it.portador.cpf == portador.cpf }
    }
}