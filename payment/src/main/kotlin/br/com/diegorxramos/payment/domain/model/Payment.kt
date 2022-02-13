package br.com.diegorxramos.payment.domain.model

import br.com.diegorxramos.payment.domain.enum.PaymentStatus
import br.com.diegorxramos.payment.domain.recurrence.RecurrenceFrequencies
import br.com.diegorxramos.payment.domain.status.PaymentClassificationStatus
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@Table("payments")
data class Payment @PersistenceConstructor constructor(

    @Id
    val id: String? = UUID.randomUUID().toString(),
    val date: LocalDate,
    val amount: BigDecimal,
    val description: String?,
    val createdAt: LocalDate,
    val finalDate: LocalDate? = null,
    val frequency: String? = null,
    val status: PaymentStatus? = PaymentClassificationStatus().classify(date),
    val destination: String
) {

    var nextDate: LocalDate? = buildNextDate()

    private fun buildNextDate(): LocalDate? {
        val isValid = finalDate != null && frequency != null
        if (isValid) return RecurrenceFrequencies().calc(amount, finalDate!!, frequency!!)
        return null
    }
}
