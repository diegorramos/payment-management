package br.com.diegorxramos.receipt.application.service

import br.com.diegorxramos.receipt.application.dto.PaymentDto
import br.com.diegorxramos.receipt.domain.model.Receipt
import br.com.diegorxramos.receipt.domain.repository.ReceiptRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ReceiptService(private val repository: ReceiptRepository) {

    private val log = KotlinLogging.logger {}

    fun create(paymentDto: PaymentDto): Mono<Receipt> {
        val receipt = getReceipt(paymentDto)
        return repository
            .save(receipt)
            .doOnSuccess { log.info("create receipt from payment, id={}", paymentDto.id) }
    }

    private fun getReceipt(paymentDto: PaymentDto): Receipt {
        return Receipt(
            paymentId = paymentDto.id!!,
            amount = paymentDto.amount!!,
            status = paymentDto.status!!,
            paymentDate = paymentDto.date!!,
            destination = paymentDto.destination!!
        )
    }
}