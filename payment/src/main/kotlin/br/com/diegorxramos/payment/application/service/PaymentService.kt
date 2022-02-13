package br.com.diegorxramos.payment.application.service

import br.com.diegorxramos.payment.application.builder.PaymentBuilder
import br.com.diegorxramos.payment.application.dto.PaymentDto
import br.com.diegorxramos.payment.application.exception.ConflictException
import br.com.diegorxramos.payment.domain.enum.PaymentStatus
import br.com.diegorxramos.payment.domain.model.Payment
import br.com.diegorxramos.payment.domain.repository.PaymentRepository
import br.com.diegorxramos.payment.infrastructure.notification.PaymentSuccessNotification
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class PaymentService(
    private val repository: PaymentRepository,
    private val notifications: List<PaymentSuccessNotification>
) {

    fun create(dto: PaymentDto): Mono<Payment> {
        val payment = this.getPayment(dto)
        return repository
            .findByAmountAndDateAndDestination(dto.date!!, dto.amount!!, dto.destination!!)
            .flatMap(this::conflict)
            .switchIfEmpty(this.save(payment))
            .doOnSuccess(this::notify)
    }

    fun list(): Flux<Payment> {
        return repository.list()
    }

    fun listConfirmed(confirmed: PaymentStatus): Flux<Payment> {
        return repository.listConfirmed(confirmed)
    }

    fun listScheduled(scheduled: PaymentStatus): Flux<Payment> {
        return repository.listScheduled(scheduled)
    }

    private fun notify(payment: Payment) = notifications.forEach { notification -> notification.notify(payment) }

    private fun save(payment: Payment) = Mono.defer { repository.create(payment) }

    private fun conflict(payment: Payment) =
        Mono.error<Payment>(ConflictException("payment already exists, id=${payment.id}"))

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