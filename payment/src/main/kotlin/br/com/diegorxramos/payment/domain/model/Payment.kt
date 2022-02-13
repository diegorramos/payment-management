package br.com.diegorxramos.payment.domain.model

import br.com.diegorxramos.payment.domain.enum.PaymentStatus
import br.com.diegorxramos.payment.domain.status.PaymentClassificationStatus
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@Table("payments")
data class Payment @PersistenceConstructor constructor(

    @Id
    val id: String? = UUID.randomUUID().toString(),
    val date: LocalDate?,
    val amount: BigDecimal?,
    val description: String?,
    val createdAt: LocalDate?,
    val recurrence: Recurrence?,
    val status: PaymentStatus? = PaymentClassificationStatus().classify(date!!),
    val destination: String?
)
