package api.presentation.routes

import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import api.domain.model.Portador
import io.ktor.http.*

fun Application.configureRouting(){
    routing {
        route("/portadores"){
            post {
                call.receive<Portador>()
                call.respond(HttpStatusCode.Created, "Portador inserido com sucesso")
            }
            delete {

            }
        }
        route("/contas"){
            post {

            }
            get {

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

fun Application.module() {
    configureSerialization()
    configureRouting()
}