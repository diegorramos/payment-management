package br.com.diegorxramos.payment.domain.status

import br.com.diegorxramos.payment.domain.enum.PaymentStatus
import java.time.LocalDate

class PaymentClassificationStatus {

    fun classify(date: LocalDate): PaymentStatus {
        val invalid: PaymentClassification = InvalidClassification()
        val scheduled: PaymentClassification = ScheduledClassification(invalid)
        val confirmed: PaymentClassification = ConfirmedClassification(scheduled)
        return confirmed.classify(date)
    }
}