package api.domain.repositories

import api.domain.model.Conta
import api.domain.model.Portador

interface ContaRepository {
    fun create(conta: Conta)
    fun findByPortador(portador: Portador): Conta?
}