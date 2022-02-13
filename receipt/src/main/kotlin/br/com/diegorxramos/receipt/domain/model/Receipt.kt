package br.com.diegorxramos.receipt.domain.model

import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@Table("receipts")
data class Receipt @PersistenceConstructor constructor(
    val id: String? = UUID.randomUUID().toString(),
    val status: String,
    val paymentId: String,
    val amount: BigDecimal,
    val destination: String,
    val paymentDate: LocalDate
)
