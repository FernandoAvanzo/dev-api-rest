package api.application.command.handlers

import api.application.query.handlers.GetContaQueryHandler
import api.domain.*
import api.domain.model.Conta
import api.domain.model.Extrato
import api.domain.model.Operacao
import api.domain.model.Portador
import api.domain.repositories.ContaRepository
import api.domain.repositories.ExtratoRepository
import api.domain.repositories.PortadorRepository

class CreateContaCommandHandler(
    private val contaRepository: ContaRepository,
    private val portadorRepository: PortadorRepository
) {
    fun handle(command: Conta) {
        when (portadorRepository.findByCpf(command.portador.cpf)) {
            null -> apply {
                contaRepository.create(command)
                portadorRepository.create(command.portador)
            }
            else -> throw ContaRulesException("Portador with CPF ${command.portador.cpf} already exists.")
        }
    }
}

class BlockContaCommandHandler(
    private val contaRepository: ContaRepository,
    private val getContaQuery : GetContaQueryHandler
){
    fun handle(command: String?) = getContaQuery
        .handle(command)
        .run {
        contaRepository.blockAccount(
            conta = this
        )
    }
}

class UnblockContaCommandHandler(
    private val contaRepository: ContaRepository,
    private val getContaQuery : GetContaQueryHandler
){
    fun handle(command: String?) = getContaQuery
        .handle(command)
        .run {
            contaRepository.blockAccount(
                conta = this
            )
        }
}

class CreatePortadorCommandHandler(
    private val portadorRepository: PortadorRepository
) {
    fun handle(command: Portador) {
        portadorRepository.create(command)
    }
}

class CreateDepositoCommandHandler(
    private val contaRepository: ContaRepository,
    private val extratoRepository: ExtratoRepository
){
    fun handle(command: Pair<String?, Double?>) = contaRepository.run{
        command.first?.let {
            findByCpf(
                cpf = it
            )?.let { conta ->
                conta.takeIf { active-> active.bloqueado.not() }?.let {
                    val depositValue = command.second ?: throw ExtractWrongInputException("wrong value")
                    val deposit = Extrato(
                        conta = conta.copy(
                            saldo = conta.saldo + depositValue
                        ),
                        valor = depositValue,
                        operacao = Operacao.DEPOSITO
                    )
                    updateConta(deposit.conta)
                    extratoRepository.deposit(deposit)
                } ?: throw ContaInactiveException("Conta bloqueada ou inativa")
            } ?: throw ContaNotFoundException("Conta não encontrada")
        } ?: throw CpfNullException("CPF not found.")
    }
}

class CreateSaqueCommandHandler(
    private val contaRepository: ContaRepository,
    private val extratoRepository: ExtratoRepository
){
    fun handle(command: Pair<String?, Double?>) = contaRepository.run{
        command.first?.let {
            findByCpf(
                cpf = it
            )?.let { conta ->
                val withdrawInput = command.second ?: throw ExtractWrongInputException("wrong value")
                (conta.saldo - withdrawInput).takeIf{
                        withdrawPositive -> withdrawPositive >= 0
                }?.let { withdrawValue ->
                    val withdraw = Extrato(
                        conta = conta.copy(
                            saldo = withdrawValue
                        ),
                        valor = withdrawInput,
                        operacao = Operacao.SAQUE
                    )
                    updateConta(withdraw.conta)
                    extratoRepository.withdraw(withdraw)
                } ?: throw BalanceNegativeException("Insufficient balance")
            } ?: throw ContaNotFoundException("Conta with CPF ${command.first} not found.")
        } ?: throw CpfNullException("CPF not found.")
    }
}