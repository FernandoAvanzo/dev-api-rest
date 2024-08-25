package api.presentation.routes

import api.domain.model.Portador
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(){
    routing {
        route("/portadores"){
            post {
                val request = call.receive<Portador>()
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

fun Application.module() {
    configureRouting()
}