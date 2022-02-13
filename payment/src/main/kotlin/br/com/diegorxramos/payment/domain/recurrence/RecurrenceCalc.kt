package br.com.diegorxramos.payment.domain.recurrence

import br.com.diegorxramos.payment.domain.model.Recurrence
import java.math.BigDecimal
import java.time.LocalDate

interface RecurrenceCalc {

    fun calc(amount: BigDecimal, finalDate: LocalDate, frequency: String): Recurrence
}