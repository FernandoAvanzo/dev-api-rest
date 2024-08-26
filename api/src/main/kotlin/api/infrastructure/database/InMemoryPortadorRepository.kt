package api.infrastructure.database

import api.domain.model.Portador
import api.domain.PortadorRulesException
import api.domain.isValidCPF
import api.domain.repositories.PortadorRepository

class InMemoryPortadorRepository : PortadorRepository {
    private val portadores = mutableSetOf<Portador>()

    override fun save(portador: Portador) {
        when {
            !portador.cpf.isValidCPF() -> throw PortadorRulesException(
                "Invalid CPF: ${portador.cpf}"
            )
            portadores.any { it.cpf == portador.cpf } -> throw PortadorRulesException(
                "Portador with cpf ${portador.cpf} already exists"
            )
            else -> portadores.add(portador)
        }
    }

    override fun findById(cpf: String): Portador? {
        return portadores.find { it.cpf == cpf }
    }
}