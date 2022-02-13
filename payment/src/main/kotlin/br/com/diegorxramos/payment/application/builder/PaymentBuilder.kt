package br.com.diegorxramos.payment.application.builder

import br.com.diegorxramos.payment.application.dto.RecurrenceDto
import br.com.diegorxramos.payment.domain.model.Payment
import java.math.BigDecimal
import java.time.LocalDate

class PaymentBuilder {

    data class Builder(
        var date: LocalDate? = null,
        var amount: BigDecimal? = null,
        var description: String? = null,
        var destination: String? = null,
        var createdAt: LocalDate? = null,
        var recurrenceDto: RecurrenceDto? = null
    ) {

        fun date(date: LocalDate) = apply { this.date = date }
        fun amount(amount: BigDecimal) = apply { this.amount = amount }
        fun createdAt(createdAt: LocalDate) = apply { this.createdAt = createdAt }
        fun description(description: String) = apply { this.description = description }
        fun destination(destination: String) = apply { this.destination = destination }
        fun recurrence(recurrenceDto: RecurrenceDto?) = apply { this.recurrenceDto = recurrenceDto }
        fun build(): Payment {
            return Payment(
                date = date!!,
                amount = amount!!,
                createdAt = createdAt!!,
                description = description,
                destination = destination!!,
                finalDate = recurrenceDto?.finalDate,
                frequency = recurrenceDto?.frequency
            )
        }
    }
}