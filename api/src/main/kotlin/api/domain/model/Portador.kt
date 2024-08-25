package api.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Portador(val cpf: String, val nome: String)