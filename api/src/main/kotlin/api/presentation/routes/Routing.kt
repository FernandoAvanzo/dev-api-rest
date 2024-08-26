package api.presentation.routes

import api.application.command.handlers.*
import api.application.query.handlers.GetContaQueryHandler
import api.di.apiModule
import api.domain.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import api.domain.model.Portador
import api.domain.model.Conta
import api.domain.model.Extrato
import io.ktor.http.*
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin

fun Application.configureRouting(){
    val createPortadorCommandHandler : CreatePortadorCommandHandler by inject()
    val createContaCommandHandler: CreateContaCommandHandler by inject()
    val getContaQueryHandler: GetContaQueryHandler by inject()
    val blockContaCommandHandler :BlockContaCommandHandler by inject()
    val unblockContaCommandHandler :UnblockContaCommandHandler by inject()
    val createDepositoCommandHandler : CreateDepositoCommandHandler by inject()

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
                getContaQueryHandler.runCatching {
                    handle(
                        call.request.queryParameters["cpf"]
                    ).apply {
                        call.respond(
                        HttpStatusCode.OK, mapOf(
                            "saldo" to saldo,
                            "numero" to numero,
                            "agencia" to agencia
                        ).toString())
                    }
                }.onFailure {
                    when(it){
                        is CpfNullException -> call.respond(
                            HttpStatusCode.BadRequest,
                            it.localizedMessage
                        )
                        is ContaNotFoundException -> call.respond(
                            HttpStatusCode.NotFound, "Conta not found"
                        )
                        else -> call.respond(
                            HttpStatusCode.InternalServerError,
                            "Internal server error"
                        )
                    }
                }
            }
            delete {

            }
        }
        route("/contas/bloqueio") {
            post {
                blockContaCommandHandler.runCatching {
                    handle(call.request.queryParameters["cpf"])
                    call.respond(HttpStatusCode.OK, "Conta bloqueada com sucesso")
                }.onFailure {
                    when (it) {
                        is CpfNullException -> call.respond(
                            HttpStatusCode.BadRequest,
                            it.localizedMessage
                        )
                        is ContaNotFoundException -> call.respond(
                            HttpStatusCode.NotFound,
                            "Conta não encontrada"
                        )
                        else -> call.respond(
                            HttpStatusCode.InternalServerError,
                            "Internal server error"
                        )
                    }
                }
            }
        }
        route("/contas/desbloqueio"){
            post {
                unblockContaCommandHandler.runCatching {
                    handle(call.request.queryParameters["cpf"])
                    call.respond(HttpStatusCode.OK, "Conta desbloqueada com sucesso")
                }.onFailure {
                    when (it) {
                        is CpfNullException -> call.respond(
                            HttpStatusCode.BadRequest,
                            it.localizedMessage
                        )
                        is ContaNotFoundException -> call.respond(
                            HttpStatusCode.NotFound,
                            "Conta não encontrada"
                        )
                        else -> call.respond(
                            HttpStatusCode.InternalServerError,
                            "Internal server error"
                        )
                    }
                }
            }
        }
        route("/transacoes/deposito") {
            post {
                createDepositoCommandHandler.runCatching {
                    val request = call.receive<Extrato>()
                    handle(
                        command = request.conta.portador.cpf to
                                request.valor
                    )
                }.onSuccess {
                    call.respond(HttpStatusCode.OK, "Depósito realizado com sucesso")
                }.onFailure {
                    when (it) {
                        is ContaNotFoundException -> call.respond(
                            HttpStatusCode.NotFound,
                            "Conta não encontrada"
                        )
                        is ContaInactiveException -> call.respond(
                            HttpStatusCode.BadRequest,
                            it.localizedMessage
                        )
                        is CpfNullException -> call.respond(
                            HttpStatusCode.BadRequest,
                            it.localizedMessage
                        )
                        is ExtractWrongInputException -> call.respond(
                            HttpStatusCode.BadRequest,
                            it.localizedMessage
                        )
                        else -> throw it
                    }
                }
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