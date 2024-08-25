package api.application.command.handlers

import api.domain.model.Portador
import api.domain.repositories.PortadorRepository
import io.mockk.*
import kotlin.test.Test

class CreatePortadorCommandHandlerTest {

    private val portadorRepository = mockk<PortadorRepository>(relaxed = true)
    private val commandHandler = CreatePortadorCommandHandler(portadorRepository)
    
    @Test
    fun `should create a new Portador successfully`() {

        commandHandler.handle(Portador(
            cpf ="009.563.109-74",
            nome ="Test User"
        ))

        verify { portadorRepository.save(any<Portador>()) }
    }
}