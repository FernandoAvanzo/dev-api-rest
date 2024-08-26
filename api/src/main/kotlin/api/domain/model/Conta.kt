package api.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Conta(
    val cpfPortador: String,
    val agencia: String,
    val numero: String,
    val saldo: Double
)