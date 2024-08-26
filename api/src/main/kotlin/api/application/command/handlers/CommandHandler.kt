package api.application.command.handlers

import api.application.query.handlers.GetContaQueryHandler
import api.domain.ContaRulesException
import api.domain.model.Conta
import api.domain.model.Portador
import api.domain.repositories.ContaRepository
import api.domain.repositories.PortadorRepository
import java.util.PriorityQueue

class CreateContaCommandHandler(
    private val contaRepository: ContaRepository,
    private val portadorRepository: PortadorRepository
) {
    fun handle(command: Conta) {
        when (portadorRepository.findByCpf(command.portador.cpf)) {
            null -> apply {
                contaRepository.create(command)
                portadorRepository.create(command.portador)
            }
            else -> throw ContaRulesException("Portador with CPF ${command.portador.cpf} already exists.")
        }
    }
}

class BlockContaCommandHandler(
    private val contaRepository: ContaRepository,
    private val getContaQuery : GetContaQueryHandler
){
    fun handle(command: String?) = getContaQuery
        .handle(command)
        .run {
        contaRepository.blockAccount(
            conta = this
        )
    }
}

class UnblockContaCommandHandler(
    private val contaRepository: ContaRepository,
    private val getContaQuery : GetContaQueryHandler
){
    fun handle(command: String?) = getContaQuery
        .handle(command)
        .run {
            contaRepository.blockAccount(
                conta = this
            )
        }
}

class CreatePortadorCommandHandler(
    private val portadorRepository: PortadorRepository
) {
    fun handle(command: Portador) {
        portadorRepository.create(command)
    }
}