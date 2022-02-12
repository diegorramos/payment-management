package br.com.diegorxramos.payment.infrastructure.notification

import br.com.diegorxramos.payment.domain.model.Payment
import br.com.diegorxramos.payment.infrastructure.enum.Exchange
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate
import org.springframework.stereotype.Component

@Component
class PaymentCreatedNotification(private val amqpTemplate: RabbitMessagingTemplate) : PaymentSuccessNotification {

    override fun notify(payment: Payment) {
        val message = mapOf(
            "date" to payment.date,
            "paymentId" to payment.id,
            "amount" to payment.amount,
            "status" to payment.status
        )
        amqpTemplate.convertAndSend(Exchange.RECEIPT_EXCHANGE, message)
    }
}