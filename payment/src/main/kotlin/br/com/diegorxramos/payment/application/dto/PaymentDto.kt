package br.com.diegorxramos.payment.application.dto

import java.math.BigDecimal
import java.time.LocalDate

data class PaymentDto(
    val date: LocalDate?,
    val amount: BigDecimal?,
    val description: String?,
    val destination: String?,
    val createdAt: LocalDate?,
    var recurrence: RecurrenceDto? = null,
) {

    @Throws(IllegalArgumentException::class)
    fun valid() {
        if (this.date == null) throw IllegalArgumentException("payment date is required")
        if (this.amount == null) throw IllegalArgumentException("payment amount is required")
        if (this.createdAt == null) throw IllegalArgumentException("payment createdAt is required")
        if (!isValidDestination(this.destination)) throw IllegalArgumentException("payment destination invalid")
    }

    private fun isValidDestination(destination: String?): Boolean {
        val isMail = destination?.matches("\\S+@\\S+\\.\\S+".toRegex()) ?: false
        val isCpf = destination?.matches("^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}\$".toRegex()) ?: false
        val isUUID = destination?.matches("^[0-9a-fA-F]{8}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{4}\\b-[0-9a-fA-F]{12}\$".toRegex()) ?: false
        return isMail || isCpf || isUUID
    }
}
