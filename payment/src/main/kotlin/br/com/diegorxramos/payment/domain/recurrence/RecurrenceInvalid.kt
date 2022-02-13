package br.com.diegorxramos.payment.domain.recurrence

import java.math.BigDecimal
import java.time.LocalDate

class RecurrenceInvalid : RecurrenceCalculator() {

    override fun getLimitMonths(): Long {
        TODO("Not yet implemented")
    }

    override fun getBasePaymentAmount(): BigDecimal {
        TODO("Not yet implemented")
    }

    override fun calc(amount: BigDecimal, finalDate: LocalDate, frequency: String): LocalDate {
        throw IllegalArgumentException("final date of recurrence is invalid")
    }
}