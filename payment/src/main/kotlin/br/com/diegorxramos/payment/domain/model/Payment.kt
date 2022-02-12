package br.com.diegorxramos.payment.domain.model

import br.com.diegorxramos.payment.domain.enum.PaymentStatus
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

data class Payment(
    val id: String,
    val date: LocalDate,
    val amount: BigDecimal,
    val description: String?,
    val createdAt: LocalDate,
    val recurrence: Recurrence,
    val destination: Destination
) {

    var status: PaymentStatus = setStatus(date)

    constructor(
        date: LocalDate,
        amount: BigDecimal,
        description: String?,
        createdAt: LocalDate,
        recurrence: Recurrence,
        destination: Destination
    ) : this(UUID.randomUUID().toString(), date, amount, description, createdAt, recurrence, destination)

    private fun setStatus(date: LocalDate) = when {
        date.compareTo(LocalDate.now()) == 0 -> PaymentStatus.CONFIRMED
        date.compareTo(LocalDate.now()) == 1 -> PaymentStatus.SCHEDULED
        else -> throw IllegalArgumentException("invalid payment date")
    }
}
