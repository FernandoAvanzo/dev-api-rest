package api.application.query.handlers

import api.domain.ContaNotFoundException
import api.domain.CpfNullException
import api.domain.model.Conta
import api.domain.repositories.ContaRepository

class GetContaQueryHandler(
    private val contaRepository: ContaRepository
) {
    fun handle(query: String?): Conta = when {
        query.isNullOrEmpty() -> throw CpfNullException("Query cannot be null or empty")
        else -> {
            contaRepository.findByCpf(query)
                ?: throw ContaNotFoundException("Conta not found for the given portador")
        }
    }
}