package api.presentation

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(){
    routing {
        route("/portadores"){
            get {
                call.respondText("Hello World! agora foi")
            }
        }
    }
}