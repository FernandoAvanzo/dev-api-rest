package api.domain.model

import api.domain.generateRandomId
import api.domain.getCurrentDateTime
import kotlinx.serialization.Serializable


@Serializable
data class Transacao(
    val conta: Conta,
    val valor: Double,
    val operacao: Operacao = Operacao.DEFAULT,
    val date: String = getCurrentDateTime(),
    val id: Long = generateRandomId()
)