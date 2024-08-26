package api.domain

class PortadorRulesException(message: String) : Exception(message)
class ContaRulesException(message: String) : Exception(message)
class CpfNullException(message: String) : Exception(message)
class ContaNotFoundException(message: String) : Exception(message)
class BalanceNegativeException(message: String) : Exception(message)
class ExtractWrongInputException(message: String) : Exception(message)