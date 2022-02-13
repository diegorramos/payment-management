package br.com.diegorxramos.payment.application.dto

import br.com.diegorxramos.payment.domain.model.Payment
import java.math.BigDecimal
import java.time.LocalDate

data class PaymentDto(
    val date: LocalDate?,
    val amount: BigDecimal?,
    val description: String?,
    val destination: String?,
    val createdAt: LocalDate?,
    val recurrenceDto: RecurrenceDto?,
) {

    @Throws(IllegalArgumentException::class)
    fun valid() {
        if (this.date == null) throw IllegalArgumentException("payment date is required")
        if (this.amount == null) throw IllegalArgumentException("payment amount is required")
        if (this.createdAt == null) throw IllegalArgumentException("payment createdAt is required")
        if (!isValidDestination(this.destination)) throw IllegalArgumentException("payment destination invalid")
    }

    fun paymentOf(): Payment {
        return Payment(
            date = this.date!!,
            amount = this.amount!!,
            createdAt = this.createdAt,
            description = this.description,
            destination = this.destination!!,
            recurrence = null
        )
    }

    private fun isValidDestination(destination: String?): Boolean {
        val isMail = destination?.matches("\\S+@\\S+\\.\\S+".toRegex()) ?: false
        val isCpf = destination?.matches("^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}\$".toRegex()) ?: false
        return isMail || isCpf
    }
}
