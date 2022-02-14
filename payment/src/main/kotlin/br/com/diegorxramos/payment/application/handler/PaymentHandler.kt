package br.com.diegorxramos.payment.application.handler

import br.com.diegorxramos.payment.application.dto.PaymentDto
import br.com.diegorxramos.payment.application.service.PaymentService
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
        return service
            .delete(request.pathVariable("id"))
            .flatMap { payment -> this.response(payment, HttpStatus.NO_CONTENT) }
            .onErrorResume(::handler)
    }

    fun update(request: ServerRequest): Mono<ServerResponse> {
        return request
            .bodyToMono(PaymentDto::class.java)
            .flatMap { payment -> service.update(request.pathVariable("id"), payment) }
            .flatMap { payment -> this.response(payment, HttpStatus.OK) }
            .onErrorResume(::handler)
    }

    fun list(request: ServerRequest): Mono<ServerResponse> {
        return service.list()
            .collectList()
            .flatMap { payments -> response(payments, HttpStatus.OK) }
    }

    fun listConfirmed(request: ServerRequest): Mono<ServerResponse> {
        return service.listConfirmed()
            .collectList()
            .flatMap { payments -> response(payments, HttpStatus.OK) }
    }

    fun listScheduled(request: ServerRequest): Mono<ServerResponse> {
        return service.listScheduled()
            .collectList()
            .flatMap { payments -> response(payments, HttpStatus.OK) }
    }

    private fun create(paymentDto: PaymentDto): Mono<Payment> {
        paymentDto.valid()
        return service.create(paymentDto)
    }
}