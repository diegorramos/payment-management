package br.com.diegorxramos.payment.infrastructure.repository

import br.com.diegorxramos.payment.domain.enum.PaymentStatus
import br.com.diegorxramos.payment.domain.model.Destination
import br.com.diegorxramos.payment.domain.model.Payment
import br.com.diegorxramos.payment.domain.repository.PaymentRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.LocalDate

@Repository
class PaymentRepositoryImpl : PaymentRepository {

    val payments = mutableMapOf<String, Payment>()

    override fun create(payment: Payment): Mono<Payment> {
        payments[payment.id] = payment
        return Mono.justOrEmpty(payment)
    }

    override fun list(): Flux<Payment> {
        return Flux.fromIterable(payments.values)
    }

    override fun listConfirmed(confirmed: PaymentStatus): Flux<Payment> {
        return Flux
            .fromIterable(payments.values)
            .filter { payment -> payment.status == confirmed }
    }

    override fun listScheduled(scheduled: PaymentStatus): Flux<Payment> {
        return Flux
            .fromIterable(payments.values)
            .filter { payment -> payment.status == scheduled }
    }

    override fun findByAmountAndDateAndDestination(
        date: LocalDate, amount: BigDecimal, destination: Destination): Mono<Payment> {

        fun exists(payment: Payment, date: LocalDate, amount: BigDecimal, destination: Destination) =
            payment.date == date && payment.amount == amount && payment.destination.key == destination.key

        val payment = payments
            .values
            .find { p -> exists(p, date, amount, destination) }

        return Mono.justOrEmpty(payment)
    }

    override fun delete(): Mono<Void> {
        payments.clear()
        return Mono.empty()
    }

    override fun delete(id: String): Mono<Void> {
        TODO("Not yet implemented")
    }
}