package api.presentation

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(){
    routing {
        route("/portadores"){
            post {

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
    }
}