package api.domain.model

// Validação simples de CPF (implementação fictícia)
private fun String.isValidCPF(): Boolean {
    // Aqui você deve implementar a lógica real de validação de CPF
    return this.matches(Regex("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}"))
}