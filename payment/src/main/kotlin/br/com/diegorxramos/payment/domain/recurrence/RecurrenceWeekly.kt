package br.com.diegorxramos.payment.domain.recurrence

import br.com.diegorxramos.payment.domain.enum.RecurrenceFrequency
import java.math.BigDecimal
import java.time.LocalDate

class RecurrenceWeekly(private val next: RecurrenceCalculator) : RecurrenceCalculator() {

    override fun getLimitMonths() = 12L
    override fun getBasePaymentAmount() = BigDecimal("50.0")

    override fun calc(amount: BigDecimal, finalDate: LocalDate, frequency: String): LocalDate {
        if (frequency == RecurrenceFrequency.WEEKLY.toString()) {
            if (isValidRecurrence(amount, finalDate))
                return LocalDate.now().plusWeeks(1)
        }
        return next.calc(amount, finalDate, frequency)
    }
}