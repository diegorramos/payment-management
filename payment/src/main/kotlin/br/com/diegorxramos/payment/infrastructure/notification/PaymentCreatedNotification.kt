package br.com.diegorxramos.payment.infrastructure.notification

import br.com.diegorxramos.payment.domain.model.Payment
import br.com.diegorxramos.payment.infrastructure.enum.Exchange
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate
import org.springframework.stereotype.Component

@Component
class PaymentCreatedNotification(
    private val objectMapper: ObjectMapper,
    private val amqpTemplate: RabbitMessagingTemplate

    ) : PaymentSuccessNotification {

    override fun notify(payment: Payment) {
        amqpTemplate.convertAndSend(Exchange.RECEIPT_EXCHANGE, objectMapper.writeValueAsString(payment))
    }
}