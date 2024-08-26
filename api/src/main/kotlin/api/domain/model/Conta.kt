package api.domain.model

import api.domain.generateRandomFourDigit
import api.domain.generateRandomSixDigit
import kotlinx.serialization.Serializable

@Serializable
data class Conta(
    val cpfPortador: String,
    val agencia: String = generateRandomFourDigit(),
    val numero: String = generateRandomSixDigit(),
    val saldo: Double = 0.0
)