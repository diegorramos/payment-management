package br.com.diegorxramos.payment.application.router

import br.com.diegorxramos.payment.application.handler.PaymentHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RequestPredicates.*
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration(proxyBeanMethods = false)
class PaymentRouter {

    @Bean
    fun route(handler: PaymentHandler): RouterFunction<ServerResponse> {
        return RouterFunctions
            .route(GET("/payments").and(accept(MediaType.APPLICATION_JSON)), handler::list)

            .andRoute(POST("/payments").and(accept(MediaType.APPLICATION_JSON)), handler::create)

            .andRoute(DELETE("/payments/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::delete)

            .andRoute(GET("/payments/confirmed").and(accept(MediaType.APPLICATION_JSON)), handler::listConfirmed)

            .andRoute(GET("/payments/scheduled").and(accept(MediaType.APPLICATION_JSON)), handler::listScheduled)
    }
}