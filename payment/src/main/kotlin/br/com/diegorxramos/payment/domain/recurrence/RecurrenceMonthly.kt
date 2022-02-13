package br.com.diegorxramos.payment.domain.recurrence

import br.com.diegorxramos.payment.domain.enum.RecurrenceFrequency
import java.math.BigDecimal
import java.time.LocalDate

class RecurrenceMonthly(private val next: RecurrenceCalculator) : RecurrenceCalculator() {

    override fun getLimitMonths() = 24L
    override fun getBasePaymentAmount() = BigDecimal("100.0")

    override fun calc(amount: BigDecimal, finalDate: LocalDate, frequency: String): LocalDate {
        if (frequency == RecurrenceFrequency.MENSAL.toString()) {
            if (isValidRecurrence(amount, finalDate))
                return LocalDate.now().plusMonths(1)
        }
        return next.calc(amount, finalDate, frequency)
    }
}