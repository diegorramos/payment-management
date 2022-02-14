package br.com.diegorxramos.payment.application.service

import br.com.diegorxramos.payment.application.builder.PaymentBuilder
import br.com.diegorxramos.payment.application.dto.PaymentDto
import br.com.diegorxramos.payment.application.exception.ConflictException
import br.com.diegorxramos.payment.domain.model.Payment
import br.com.diegorxramos.payment.domain.repository.PaymentRepository
import br.com.diegorxramos.payment.infrastructure.notification.PaymentSuccessNotification
import mu.KotlinLogging
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class PaymentService(
    private val repository: PaymentRepository,
    private val notifications: List<PaymentSuccessNotification>
) {

    private val log = KotlinLogging.logger {}

    fun create(dto: PaymentDto): Mono<Payment> {
        val payment = this.getPayment(dto)
        return repository
            .findByAmountAndDateAndDestination(dto.date!!, dto.amount!!, dto.destination!!)
            .flatMap(this::conflict)
            .switchIfEmpty(this.save(payment))
            .doOnSuccess(this::notify)
            .doOnSuccess {
                log.info("payment created with amount={} date={} destination={}", dto.amount, dto.date, dto.destination)
            }
    }

    fun update(id: String, dto: PaymentDto): Mono<Payment> {
        val payment = this.getPayment(dto)
        return repository.update(id, payment)
    }

    fun delete(id: String): Mono<Int> {
        return repository
            .delete(id)
            .doOnSuccess { log.info("deleted payment, id={}", id) }
    }

    fun list() = repository.list()

    fun listConfirmed() = repository.listConfirmed()

    fun listScheduled() = repository.listScheduled()

    private fun notify(payment: Payment) = notifications.forEach { notification -> notification.notify(payment) }

    private fun save(payment: Payment) = Mono.defer { repository.create(payment) }

    private fun conflict(payment: Payment): Mono<Payment> {
        log.warn("payment already exists with amount={} date={} destination={}",
            payment.amount, payment.date, payment.destination
        )
        return Mono.error(ConflictException("payment already exists, id=${payment.id}"))
    }

    private fun getPayment(dto: PaymentDto): Payment {
        return PaymentBuilder.Builder()
            .date(dto.date!!)
            .amount(dto.amount!!)
            .createdAt(dto.createdAt!!)
            .description(dto.description!!)
            .destination(dto.destination!!)
            .recurrence(dto.recurrence)
            .build()
    }
}