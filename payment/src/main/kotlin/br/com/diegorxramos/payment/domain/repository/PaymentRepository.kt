package br.com.diegorxramos.payment.domain.repository

import br.com.diegorxramos.payment.domain.model.Payment
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.LocalDate

interface PaymentRepository {

    fun list(): Flux<Payment>
    fun delete(id: String): Mono<Int>
    fun listConfirmed(): Flux<Payment>
    fun listScheduled(): Flux<Payment>
    fun create(payment: Payment): Mono<Payment>
    fun update(id: String, payment: Payment): Mono<Payment>
    fun findByAmountAndDateAndDestination(date: LocalDate, amount: BigDecimal, destination: String): Mono<Payment>
}