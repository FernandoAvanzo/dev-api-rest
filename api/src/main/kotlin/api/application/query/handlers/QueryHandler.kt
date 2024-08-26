package api.application.query.handlers

import api.domain.model.Conta
import api.domain.model.Portador
import api.domain.repositories.ContaRepository

class GetContaQueryHandler(
    private val contaRepository: ContaRepository
) {
    fun handle(query: Portador): Conta? = contaRepository.findByPortador(query)
}