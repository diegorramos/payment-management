package br.com.diegorxramos.payment.infrastructure.notification

import br.com.diegorxramos.payment.domain.model.Payment
import br.com.diegorxramos.payment.infrastructure.enum.Exchange.RECEIPT_EXCHANGE
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate
import org.springframework.stereotype.Component

@Component
class GenerateReceiptNotification(
    private val objectMapper: ObjectMapper,
    private val amqpTemplate: RabbitMessagingTemplate

    ) : PaymentSuccessNotification {

    private val log = KotlinLogging.logger {}

    override fun notify(payment: Payment) {
        amqpTemplate.convertAndSend(RECEIPT_EXCHANGE, RECEIPT_EXCHANGE, objectMapper.writeValueAsString(payment))
        log.info("sent to generate receipt payment={}", payment)
    }
}