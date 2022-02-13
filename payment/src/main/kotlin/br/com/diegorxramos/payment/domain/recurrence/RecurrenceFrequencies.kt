package br.com.diegorxramos.payment.domain.recurrence

import java.math.BigDecimal
import java.time.LocalDate

class RecurrenceFrequencies {

    fun calc(amount: BigDecimal, finalDate: LocalDate, frequency: String): LocalDate {
        val invalid = RecurrenceInvalid()
        val semiannual = RecurrenceSemiannual(invalid)
        val quarterly = RecurrenceQuarterly(semiannual)
        val monthly = RecurrenceMonthly(quarterly)
        val weekly = RecurrenceWeekly(monthly)
        return weekly.calc(amount, finalDate, frequency)
    }
}