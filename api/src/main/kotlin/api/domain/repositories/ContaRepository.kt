package api.domain.repositories

import api.domain.model.Conta

interface ContaRepository {
    fun create(conta: Conta)
    fun findByCpf(cpf: String): Conta?
}