package br.com.diegorxramos.receipt.domain.repository

import br.com.diegorxramos.receipt.domain.model.Receipt
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ReceiptRepository {

    fun list(): Flux<Receipt>
    fun save(receipt: Receipt): Mono<Receipt>
}