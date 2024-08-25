package api.presentation.routes

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class RouteTests {

    @Test
    fun `test POST route - inserir novo portador`() = testApplication {
        application {

        }
        val client = createClient {
            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                json()
            }
        }
        val response = client.post("/portadores") {
            contentType(ContentType.Application.Json)
            setBody("""{"cpf":"123.456.789-00", "nome":"Fulano de Tal"}""")
        }
        assertEquals(HttpStatusCode.Created, response.status)
        assertEquals("Portador inserido com sucesso", response.bodyAsText())
    }

}