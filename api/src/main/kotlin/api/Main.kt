package api

import api.presentation.routes.configureRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun Application.module() {
    configureRouting()
}

fun main() {
    embeddedServer(
        Netty,
        port = 9292,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}