package api

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*


@Suppress("UnusedReceiverParameter")
fun Application.module() {

}

fun main() {
    embeddedServer(
        Netty,
        port = 9292,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}