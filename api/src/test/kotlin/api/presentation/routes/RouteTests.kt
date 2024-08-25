package api.presentation.routes

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import org.koin.test.KoinTest
import kotlin.test.Test
import kotlin.test.assertEquals

class RouteTests : KoinTest {

    @Test
    fun `test POST route - inserir novo portador`() = testApplication {
        val client = createClient {
            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
        val response = client.post("/portadores") {
            contentType(ContentType.Application.Json)
            setBody("""{"cpf":"009.563.109-74", "nome":"Fulano de Tal"}""")
        }
        assertEquals(HttpStatusCode.Created, response.status)
        assertEquals("Portador inserido com sucesso", response.bodyAsText())
    }


    @Test
    fun `test POST route - inserir portador com CPF inválido`() = testApplication {
        val client = createClient {
            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
        val response = client.post("/portadores") {
            contentType(ContentType.Application.Json)
            setBody("""{"cpf":"invalid-cpf", "nome":"Fulano de Tal"}""")
        }
        assertEquals(HttpStatusCode.UnprocessableEntity, response.status)
        assertEquals("Invalid CPF: invalid-cpf", response.bodyAsText())
    }


    @Test
    fun `test POST route - inserir portador com CPF já existente`() = testApplication {
        val client = createClient {
            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }

        val firstResponse = client.post("/portadores") {
            contentType(ContentType.Application.Json)
            setBody("""{"cpf":"009.563.109-74", "nome":"Fulano de Tal"}""")
        }
        assertEquals(HttpStatusCode.Created, firstResponse.status)
        assertEquals("Portador inserido com sucesso", firstResponse.bodyAsText())

        val secondResponse = client.post("/portadores") {
            contentType(ContentType.Application.Json)
            setBody("""{"cpf":"009.563.109-74", "nome":"Outro Fulano"}""")
        }
        assertEquals(HttpStatusCode.UnprocessableEntity, secondResponse.status)
        assertEquals("Portador with cpf 009.563.109-74 already exists", secondResponse.bodyAsText())
    }
}