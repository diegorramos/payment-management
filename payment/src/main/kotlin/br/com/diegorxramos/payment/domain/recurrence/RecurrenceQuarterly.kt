package br.com.diegorxramos.payment.domain.recurrence

import br.com.diegorxramos.payment.domain.enum.RecurrenceFrequency
import java.math.BigDecimal
import java.time.LocalDate

class RecurrenceQuarterly(private val next: RecurrenceCalculator) : RecurrenceCalculator() {

    override fun getLimitMonths() = 36L
    override fun getBasePaymentAmount() = BigDecimal("130.0")

    override fun calc(amount: BigDecimal, finalDate: LocalDate, frequency: String): LocalDate {
        if (frequency == RecurrenceFrequency.TRIMESTRAL.toString()) {
            if (isValidRecurrence(amount, finalDate))
                return LocalDate.now().plusMonths(3)
        }
        return next.calc(amount, finalDate, frequency)
    }
}