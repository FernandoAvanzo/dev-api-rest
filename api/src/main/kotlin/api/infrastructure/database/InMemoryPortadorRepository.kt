package api.infrastructure.database

import api.domain.model.Portador
import api.domain.repositories.PortadorRepository

class InMemoryPortadorRepository : PortadorRepository {
    private val portadores = mutableSetOf<Portador>()

    override fun save(user: Portador) {
        TODO("Not yet implemented")
    }

    override fun findById(cpf: String): Portador? {
        TODO("Not yet implemented")
    }
}