package api.domain.repositories

import api.domain.model.Conta

interface ContaRepository {
    fun create(conta: Conta)
    fun findByCpf(cpf: String): Conta?
    fun blockAccount(conta: Conta)
    fun unBlockAccount(conta: Conta)
    fun updateConta(conta: Conta)
}