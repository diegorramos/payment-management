package br.com.diegorxramos.payment.domain.recurrence

import br.com.diegorxramos.payment.domain.enum.RecurrenceFrequency
import java.math.BigDecimal
import java.time.LocalDate

class RecurrenceSemiannual(private val next: RecurrenceCalculator) : RecurrenceCalculator() {

    override fun getLimitMonths() = 42L
    override fun getBasePaymentAmount() = BigDecimal("150.0")

    override fun calc(amount: BigDecimal, finalDate: LocalDate, frequency: String): LocalDate {
        if (frequency == RecurrenceFrequency.SEMESTRAL.toString()) {
            if (isValidRecurrence(amount, finalDate))
                return LocalDate.now().plusMonths(6)
        }
        return next.calc(amount, finalDate, frequency)
    }
}