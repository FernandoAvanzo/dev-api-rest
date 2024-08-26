package api.infrastructure.database

import api.domain.model.Conta
import api.domain.repositories.ContaRepository

class InMemoryContaRepository :ContaRepository {
    private val contas = mutableSetOf<Conta>()

    override fun create(conta: Conta) {
        contas.add(conta)
    }

    override fun findByCpf(cpf: String): Conta? {
        return contas.find { it.portador.cpf == cpf }
    }

    override fun blockAccount(conta: Conta) {
        contas.removeIf { it.portador.cpf == conta.portador.cpf }
        contas.add(conta.copy(bloqueado = true))
    }

    override fun unBlockAccount(conta: Conta) {
        contas.removeIf { it.portador.cpf == conta.portador.cpf }
        contas.add(conta.copy(bloqueado = false))
    }

    override fun updateConta(conta: Conta) {
        contas.removeIf { it.portador.cpf == conta.portador.cpf }
        contas.add(conta)
    }
}