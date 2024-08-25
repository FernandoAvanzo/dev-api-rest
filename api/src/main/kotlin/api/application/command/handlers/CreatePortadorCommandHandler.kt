package api.application.command.handlers

import api.domain.model.Portador
import api.domain.repositories.PortadorRepository

class CreatePortadorCommandHandler(
    private val portadorRepository: PortadorRepository
) {
    fun handle(command: Portador) {
        portadorRepository.save(command)
    }
}