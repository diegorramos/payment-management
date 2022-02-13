package br.com.diegorxramos.receipt.infrastructure.repository

import br.com.diegorxramos.receipt.domain.model.Receipt
import br.com.diegorxramos.receipt.domain.repository.ReceiptRepository
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
class ReceiptRepositoryImpl(private val reactiveTemplate: R2dbcEntityTemplate) : ReceiptRepository {

    override fun save(receipt: Receipt) = reactiveTemplate.insert(receipt)

    override fun list(): Flux<Receipt> {
        return reactiveTemplate.select(Receipt::class.java)
            .from("receipts")
            .all()
    }
}