package br.com.diegorxramos.payment.domain.recurrence

import br.com.diegorxramos.payment.domain.model.Recurrence
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDate

@Component
class RecurrenceWeekly : RecurrenceCalc {

    override fun calc(amount: BigDecimal, finalDate: LocalDate, frequency: String): Recurrence {
        return Recurrence(frequency,finalDate, finalDate)
    }
}