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
    fun `test POST route - inserir portador com CPF invalido`() = testApplication {
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
    fun `test POST route - inserir portador com CPF ja existente`() = testApplication {
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


    @Test
    fun `test POST route - criar nova conta`() = testApplication {
        val client = createClient {
            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
        val response = client.post("/contas") {
            contentType(ContentType.Application.Json)
            setBody("""{"portador":{"cpf":"009.563.109-74"}}""")
        }
        assertEquals(HttpStatusCode.Created, response.status)
        assertEquals("Conta criada com sucesso", response.bodyAsText())
    }


    @Test
    fun `test POST route - criar nova conta com CPF ja existente`() = testApplication {
        val client = createClient {
            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }

        val portadorResponse = client.post("/portadores") {
            contentType(ContentType.Application.Json)
            setBody("""{"cpf":"009.563.109-74", "nome":"Fulano de Tal"}""")
        }
        assertEquals(HttpStatusCode.Created, portadorResponse.status)
        assertEquals("Portador inserido com sucesso", portadorResponse.bodyAsText())

        val contaResponse = client.post("/contas") {
            contentType(ContentType.Application.Json)
            setBody("""{"portador":{"cpf":"009.563.109-74"}}""")
        }
        assertEquals(HttpStatusCode.UnprocessableEntity, contaResponse.status)
        assertEquals("Portador with CPF 009.563.109-74 already exists.", contaResponse.bodyAsText())
    }


    @Test
    fun `test GET route - consultar conta`() = testApplication {
        val client = createClient {
            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }
        val contaResponse = client.post("/contas") {
            contentType(ContentType.Application.Json)
            setBody(
                """
                    {"portador":    {"cpf":"009.563.109-74", 
                                     "nome":"Fulano de Tal"},
                    "agencia": "0001", 
                    "numero": "1234567890"}
                    """
            )
        }
        assertEquals(HttpStatusCode.Created, contaResponse.status)

        val response = client.get("/contas") {
            url {
                parameters.append("cpf", "009.563.109-74")
            }
        }

        assertEquals(HttpStatusCode.OK, response.status)
        val responseBody = response.bodyAsText()
        val expectedResponse = "{saldo=0.0, numero=1234567890, agencia=0001}"
        assertEquals(expectedResponse, responseBody)
    }


    @Test
    fun `test POST route - bloquear conta`() = testApplication {
        val client = createClient {
            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }

        // Test blocking an existing account
        val contaCriadaResponse = client.post("/contas") {
            contentType(ContentType.Application.Json)
            setBody("""{"portador":{"cpf":"009.563.109-74", "nome":"Fulano de Tal"}, "agencia":"0001", "numero":"1234567890"}""")
        }
        assertEquals(HttpStatusCode.Created, contaCriadaResponse.status)

        val responseSuccess = client.post("/contas/bloqueio") {
            url {
                parameters.append("cpf", "009.563.109-74")
            }
        }
        assertEquals(HttpStatusCode.OK, responseSuccess.status)
        assertEquals("Conta bloqueada com sucesso", responseSuccess.bodyAsText())

        // Test blocking a non-existent account
        val responseNotFound = client.post("/contas/bloqueio") {
            url {
                parameters.append("cpf", "000.000.000-00")
            }
        }
        assertEquals(HttpStatusCode.NotFound, responseNotFound.status)
        assertEquals("Conta não encontrada", responseNotFound.bodyAsText())
    }


    @Test
    fun `test POST route - desbloquear conta`() = testApplication {
        val client = createClient {
            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }

        // Test unlocking an existing account
        val contaCriadaResponse = client.post("/contas") {
            contentType(ContentType.Application.Json)
            setBody("""{"portador":{"cpf":"009.563.109-74", "nome":"Fulano de Tal"}, "agencia":"0001", "numero":"1234567890"}""")
        }
        assertEquals(HttpStatusCode.Created, contaCriadaResponse.status)

        val responseSuccess = client.post("/contas/desbloqueio") {
            url {
                parameters.append("cpf", "009.563.109-74")
            }
        }
        assertEquals(HttpStatusCode.OK, responseSuccess.status)
        assertEquals("Conta desbloqueada com sucesso", responseSuccess.bodyAsText())

        // Test unlocking a non-existent account
        val responseNotFound = client.post("/contas/desbloqueio") {
            url {
                parameters.append("cpf", "000.000.000-00")
            }
        }
        assertEquals(HttpStatusCode.NotFound, responseNotFound.status)
        assertEquals("Conta não encontrada", responseNotFound.bodyAsText())
    }


    @Test
    fun `test POST route - depositar na conta`() = testApplication {
        val client = createClient {
            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }

        client.post("/contas") {
            contentType(ContentType.Application.Json)
            setBody("""{"portador":{"cpf":"009.563.109-74"}}""")
        }

        val depositSuccessResponse = client.post("/transacoes/deposito") {
            contentType(ContentType.Application.Json)
            setBody("""
                {"conta":{"portador":{"cpf":"009.563.109-74"}}, 
                "valor":100.0}
                """.trimIndent())
        }
        assertEquals(HttpStatusCode.OK, depositSuccessResponse.status)
        assertEquals("Depósito realizado com sucesso", depositSuccessResponse.bodyAsText())

        client.post("/contas/bloqueio") {
            url {
                parameters.append("cpf", "009.563.109-74")
            }
        }

        val depositBlockedResponse = client.post("/transacoes/deposito") {
            contentType(ContentType.Application.Json)
            setBody("""
                {"conta":{"portador":{"cpf":"009.563.109-74"}}, 
                "valor":100.0}
                """.trimIndent())
        }
        assertEquals(HttpStatusCode.BadRequest, depositBlockedResponse.status)
        assertEquals("Conta bloqueada ou inativa", depositBlockedResponse.bodyAsText())

        val depositNotFoundResponse = client.post("/transacoes/deposito") {
            contentType(ContentType.Application.Json)
            setBody("""
                {"conta":{"portador":{"cpf":"069.731.949-07"}}, 
                "valor":100.0}
                """.trimIndent())
        }
        assertEquals(HttpStatusCode.NotFound, depositNotFoundResponse.status)
        assertEquals("Conta não encontrada", depositNotFoundResponse.bodyAsText())
    }


    @Test
    fun `test POST route - sacar na conta`() = testApplication {
        val client = createClient {
            install(io.ktor.client.plugins.contentnegotiation.ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
        }

        client.post("/contas") {
            contentType(ContentType.Application.Json)
            setBody("""{"portador":{"cpf":"009.563.109-74"}}""")
        }

        val depositoResponse = client.post("/transacoes/deposito") {
            contentType(ContentType.Application.Json)
            setBody(
                """
            {"conta":{"portador":{"cpf":"009.563.109-74"}}, "valor":200.0}
            """.trimIndent()
            )
        }
        assertEquals(HttpStatusCode.OK, depositoResponse.status)

        val withdrawalSuccessResponse = client.post("/transacoes/saque") {
            contentType(ContentType.Application.Json)
            setBody(
                """
            {"conta":{"portador":{"cpf":"009.563.109-74"}}, "valor":100.0}
            """.trimIndent()
            )
        }
        assertEquals(HttpStatusCode.OK, withdrawalSuccessResponse.status)
        assertEquals("Saque realizado com sucesso", withdrawalSuccessResponse.bodyAsText())

        client.post("/contas/bloqueio") {
            url {
                parameters.append("cpf", "009.563.109-74")
            }
        }
        val withdrawalBlockedResponse = client.post("/transacoes/saque") {
            contentType(ContentType.Application.Json)
            setBody(
                """
            {"conta":{"portador":{"cpf":"009.563.109-74"}}, "valor":50.0}
            """.trimIndent()
            )
        }
        assertEquals(HttpStatusCode.BadRequest, withdrawalBlockedResponse.status)
        assertEquals("Saldo insuficiente, conta bloqueada ou inativa", withdrawalBlockedResponse.bodyAsText())

        client.post("/contas/desbloqueio") {
            url {
                parameters.append("cpf", "009.563.109-74")
            }
        }
        val withdrawalInsufficientFundsResponse = client.post("/transacoes/saque") {
            contentType(ContentType.Application.Json)
            setBody(
                """
            {"conta":{"portador":{"cpf":"009.563.109-74"}}, "valor":200.0}
            """.trimIndent()
            )
        }
        assertEquals(HttpStatusCode.BadRequest, withdrawalInsufficientFundsResponse.status)
        assertEquals("Saldo insuficiente, conta bloqueada ou inativa", withdrawalInsufficientFundsResponse.bodyAsText())

        val withdrawalNotFoundResponse = client.post("/transacoes/saque") {
            contentType(ContentType.Application.Json)
            setBody(
                """
            {"conta":{"portador":{"cpf":"069.731.949-09"}}, "valor":100.0}
            """.trimIndent()
            )
        }
        assertEquals(HttpStatusCode.NotFound, withdrawalNotFoundResponse.status)
        assertEquals("Conta não encontrada", withdrawalNotFoundResponse.bodyAsText())
    }
    
}