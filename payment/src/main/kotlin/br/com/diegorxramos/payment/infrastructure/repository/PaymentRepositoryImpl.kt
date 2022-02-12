package br.com.diegorxramos.payment.infrastructure.repository

import br.com.diegorxramos.payment.domain.enum.PaymentStatus
import br.com.diegorxramos.payment.domain.model.Payment
import br.com.diegorxramos.payment.domain.repository.PaymentRepository
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.query.Criteria.where
import org.springframework.data.relational.core.query.Query.query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.math.BigDecimal
import java.time.LocalDate

@Repository
class PaymentRepositoryImpl(private val reactiveTemplate: R2dbcEntityTemplate) : PaymentRepository {

    val payments = mutableMapOf<String, Payment>()

    override fun create(payment: Payment) = reactiveTemplate.insert(payment)

    override fun list(): Flux<Payment> {
        return reactiveTemplate
            .select(Payment::class.java)
            .from("payments")
            .all()
    }

    override fun listConfirmed(confirmed: PaymentStatus): Flux<Payment> {
        return reactiveTemplate.select(Payment::class.java)
            .from("payments")
            .matching(query(where("status").`is`(confirmed)))
            .all()
    }

    override fun listScheduled(scheduled: PaymentStatus): Flux<Payment> {
        return reactiveTemplate.select(Payment::class.java)
            .from("payments")
            .matching(query(where("status").`is`(scheduled)))
            .all()
    }

    override fun findByAmountAndDateAndDestination(
        date: LocalDate, amount: BigDecimal, destination: String): Mono<Payment> {

        return reactiveTemplate.select(Payment::class.java)
            .from("payments")
            .matching(
                query(where("date").`is`(date)
                    .and("amount").`is`(amount)
                    .and("destination").`is`(destination)))
            .one()
    }

    override fun delete(): Mono<Void> {
        payments.clear()
        return Mono.empty()
    }

    override fun delete(id: String): Mono<Void> {
        TODO("Not yet implemented")
    }
}