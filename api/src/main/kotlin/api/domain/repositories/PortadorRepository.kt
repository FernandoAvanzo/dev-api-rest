package api.domain.repositories

import api.domain.model.Portador

interface PortadorRepository {
    fun create(portador: Portador)
    fun findByCpf(cpf: String): Portador?
}