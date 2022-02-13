package br.com.diegorxramos.payment.domain.recurrence

import java.math.BigDecimal
import java.time.LocalDate
import java.time.temporal.ChronoUnit

abstract class RecurrenceCalculator {

    abstract fun getLimitMonths(): Long
    abstract fun getBasePaymentAmount(): BigDecimal
    abstract fun calc(amount: BigDecimal, finalDate: LocalDate, frequency: String): LocalDate

    fun isValidRecurrence(amount: BigDecimal, finalDate: LocalDate): Boolean {
        return amount > getBasePaymentAmount() && isValidMonths(finalDate)
    }

    private fun isValidMonths(finalDate: LocalDate): Boolean {
        return ChronoUnit.MONTHS.between(LocalDate.now(), finalDate) <= getLimitMonths()
    }
}