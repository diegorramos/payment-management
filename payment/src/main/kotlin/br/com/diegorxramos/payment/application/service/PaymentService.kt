package br.com.diegorxramos.payment.application.service

import br.com.diegorxramos.payment.application.dto.PaymentDto
import br.com.diegorxramos.payment.application.exception.ConflictException
import br.com.diegorxramos.payment.domain.enum.PaymentStatus
import br.com.diegorxramos.payment.domain.model.Payment
import br.com.diegorxramos.payment.domain.repository.PaymentRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDate

@Service
class PaymentService(val repository: PaymentRepository) {

    fun create(dto: PaymentDto): Mono<Payment> {
        dto.valid()
        val payment =
            Payment(dto.date!!, dto.amount!!, dto.description, dto.createdAt, null, dto.destination!!)
        return repository
            .findByAmountAndDateAndDestination(dto.date, dto.amount, dto.destination)
            .flatMap { p -> Mono.error<Payment>(ConflictException("payment already exists, id=${p.id}")) }
            .switchIfEmpty(
                Mono.defer { repository.create(payment) }
            )
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
}