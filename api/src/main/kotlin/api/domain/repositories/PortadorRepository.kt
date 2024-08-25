package api.domain.repositories

import api.domain.model.Portador

interface PortadorRepository {
    fun save(user: Portador)
    fun findById(cpf: String): Portador?
}