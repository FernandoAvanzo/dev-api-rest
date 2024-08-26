package api.application.command.handlers

import api.domain.model.Conta
import api.domain.model.Portador
import api.domain.repositories.ContaRepository
import api.domain.repositories.PortadorRepository

class CreateContaCommandHandler(
    private val contaRepository: ContaRepository
) {
    fun handle(command: Conta) {
        contaRepository.create(command)
    }
}

class CreatePortadorCommandHandler(
    private val portadorRepository: PortadorRepository
) {
    fun handle(command: Portador) {
        portadorRepository.create(command)
    }
}