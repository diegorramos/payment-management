package br.com.diegorxramos.receipt.application.listener

import br.com.diegorxramos.receipt.application.dto.PaymentDto
import br.com.diegorxramos.receipt.application.service.ReceiptService
import br.com.diegorxramos.receipt.infrastructure.enum.Exchange
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class ReceiptListener(
    private val objectMapper: ObjectMapper,
    private val receiptService: ReceiptService

    ) {

    private val log = KotlinLogging.logger {}

    @RabbitListener(queues = [Exchange.PAYMENT_RECEIPT_QUEUE])
    fun onReceipt(message: String) {
        log.info("receive payment to generate receipt, payment={}", message)
        val payment = objectMapper.readValue(message, PaymentDto::class.java)
        receiptService.create(payment)
            .block()
    }
}