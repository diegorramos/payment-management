package br.com.diegorxramos.payment.domain.model

import br.com.diegorxramos.payment.domain.enum.PaymentStatus
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@Table("payments")
data class Payment @PersistenceConstructor constructor(

    @Id
    val id: String?,
    val date: LocalDate?,
    val amount: BigDecimal?,
    val description: String?,
    val createdAt: LocalDate?,
    val recurrence: Recurrence?,
    val destination: String?
) {

    var status: PaymentStatus? = date?.let { setStatus(it) }

    constructor(
        date: LocalDate,
        amount: BigDecimal,
        description: String?,
        createdAt: LocalDate?,
        recurrence: Recurrence?,
        destination: String?
    ) : this(UUID.randomUUID().toString(), date, amount, description, createdAt, recurrence, destination)

    private fun setStatus(date: LocalDate) = when {
        date.compareTo(LocalDate.now()) == 0 -> PaymentStatus.CONFIRMED
        date.compareTo(LocalDate.now()) == 1 -> PaymentStatus.SCHEDULED
        else -> throw IllegalArgumentException("invalid payment date")
    }
}
