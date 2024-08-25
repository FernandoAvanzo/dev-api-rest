package api.domain.repositories

import api.domain.model.Portador

interface PortadorRepository {
    fun save(portador: Portador)
    fun findById(cpf: String): Portador?
}