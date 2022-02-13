package br.com.diegorxramos.payment.application.handler

import br.com.diegorxramos.payment.application.dto.PaymentDto
import br.com.diegorxramos.payment.application.service.PaymentService
import br.com.diegorxramos.payment.domain.enum.PaymentStatus
import br.com.diegorxramos.payment.domain.model.Payment
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class PaymentHandler(
    val service: PaymentService

) : AbstractHandler() {

    fun create(request: ServerRequest): Mono<ServerResponse> {
        return request
            .bodyToMono(PaymentDto::class.java)
            .flatMap(::create)
            .flatMap { payment -> this.response(payment, HttpStatus.CREATED) }
            .onErrorResume(::handler)
    }

    fun delete(request: ServerRequest): Mono<ServerResponse> {
        return Mono.empty()
    }

    fun update(request: ServerRequest): Mono<ServerResponse> {
        return delete(request)
    }

    fun list(request: ServerRequest): Mono<ServerResponse> {
        return service.list()
            .collectList()
            .flatMap { payments -> response(payments, HttpStatus.OK) }
    }

    fun listConfirmed(request: ServerRequest): Mono<ServerResponse> {
        return service.listConfirmed(PaymentStatus.CONFIRMED)
            .collectList()
            .flatMap { payments -> response(payments, HttpStatus.OK) }
    }

    fun listScheduled(request: ServerRequest): Mono<ServerResponse> {
        return service.listScheduled(PaymentStatus.SCHEDULED)
            .collectList()
            .flatMap { payments -> response(payments, HttpStatus.OK) }
    }

    private fun create(paymentDto: PaymentDto): Mono<Payment> {
        paymentDto.valid()
        return service.create(paymentDto)
    }
}