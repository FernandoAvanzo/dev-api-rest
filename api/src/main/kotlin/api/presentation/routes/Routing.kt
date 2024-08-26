package api.presentation.routes

import api.application.command.handlers.CreateContaCommandHandler
import api.application.command.handlers.CreatePortadorCommandHandler
import api.di.apiModule
import api.domain.ContaRulesException
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import api.domain.model.Portador
import api.domain.PortadorRulesException
import api.domain.model.Conta
import io.ktor.http.*
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin

fun Application.configureRouting(){
    val createPortadorCommandHandler : CreatePortadorCommandHandler by inject()
    val createContaCommandHandler: CreateContaCommandHandler by inject()
    routing {
        route("/portadores"){
            post {
                createPortadorCommandHandler.runCatching {
                    handle(
                        command = call.receive<Portador>()
                    )
                    call.respond(HttpStatusCode.Created, "Portador inserido com sucesso")
                }.onFailure {
                    when(it){
                        is PortadorRulesException -> call.respond(
                            HttpStatusCode.UnprocessableEntity,
                            it.localizedMessage
                        )
                        else -> call.respond(
                            HttpStatusCode.InternalServerError,
                            it.localizedMessage
                        )
                    }
                }
            }
            delete {

            }
        }
        route("/contas"){
            post {
                createContaCommandHandler.runCatching {
                    handle(
                        command = call.receive<Conta>()
                    )
                    call.respond(HttpStatusCode.Created, "Conta criada com sucesso")
                }.onFailure {
                    when(it){
                        is ContaRulesException -> call.respond(
                            HttpStatusCode.UnprocessableEntity,
                            it.localizedMessage
                        )
                        else -> call.respond(
                            HttpStatusCode.InternalServerError,
                            it.stackTrace
                        )}
                }
            }
            get {
//                val cpf = call.request.queryParameters["cpf"]
//                if (cpf == null) {
//                    call.respond(HttpStatusCode.BadRequest, "Query parameter 'cpf' is required")
//                    return@get
//                }
//                val conta = createContaCommandHandler.portadorRepository.findByCpf(cpf)?.let {
//                    createContaCommandHandler.contaRepository.findByPortador(it)
//                }
//                if (conta == null) {
//                    call.respond(HttpStatusCode.NotFound, "Conta not found")
//                } else {
//                    call.respond(
//                        HttpStatusCode.OK, mapOf(
//                            "saldo" to conta.saldo,
//                            "numero" to conta.numero,
//                            "agencia" to conta.agencia
//                        )
//                    )
//                }
            }
            delete {

            }
        }
        route("/contas/bloqueio"){
            post {

            }
        }
        route("/contas/desbloqueio"){
            post {

            }
        }
        route("/transacoes/deposito"){
            post {

            }
        }
        route("/transacoes/saque"){
            post {

            }
        }
        route("/contas/extrato"){
            get {

            }
        }
        route("/health"){
            get("/check") {
                call.respond(HttpStatusCode.OK, "Server is healthy")
            }
        }
    }
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
        })
    }
}

fun Application.configureDI(){
    install(Koin) {
        modules(apiModule)
    }
}

fun Application.module() {
    configureSerialization()
    configureDI()
    configureRouting()
}