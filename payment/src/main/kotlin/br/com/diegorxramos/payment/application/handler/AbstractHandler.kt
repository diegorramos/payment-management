package br.com.diegorxramos.payment.application.handler

import br.com.diegorxramos.payment.application.exception.ConflictException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

abstract class AbstractHandler {

    fun <T : Any> response(body: T, status: HttpStatus): Mono<ServerResponse> {
        return ServerResponse
            .status(status)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(body))
    }

    fun handler(throwable: Throwable): Mono<ServerResponse> = when (throwable) {
        is IllegalArgumentException -> {
            ServerResponse
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("message:${throwable.message}"))
        }
        is ConflictException -> {
            ServerResponse
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("message:${throwable.message}"))
        }
        else -> {
            ServerResponse
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("message:${throwable.message}"))
        }
    }
}