package br.com.diegorxramos.payment.application.dto

import br.com.diegorxramos.payment.domain.model.Destination
import br.com.diegorxramos.payment.domain.model.Recurrence
import java.math.BigDecimal
import java.time.LocalDate

data class PaymentDto(
    val date: LocalDate?,
    val amount: BigDecimal?,
    val description: String?,
    val recurrence: Recurrence?,
    val destination: Destination?
) {

    @Throws(IllegalArgumentException::class)
    fun valid() {
        if (this.date == null) throw IllegalArgumentException("payment date is required")
        if (this.amount == null) throw IllegalArgumentException("payment amount is required")
        if (this.recurrence == null)
            throw IllegalArgumentException("payment destination is required") else this.recurrence.valid()
        if (this.destination == null)
            throw IllegalArgumentException("payment destination is required") else this.destination.valid()
    }
}
