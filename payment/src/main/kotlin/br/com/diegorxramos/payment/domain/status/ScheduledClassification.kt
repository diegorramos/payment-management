package br.com.diegorxramos.payment.domain.status

import br.com.diegorxramos.payment.domain.enum.PaymentStatus
import java.time.LocalDate

class ScheduledClassification(val next: PaymentClassification) : PaymentClassification {

    override fun classify(date: LocalDate): PaymentStatus {
        if (date.compareTo(LocalDate.now()) == 1) {
            return PaymentStatus.SCHEDULED
        }
        return next.classify(date)
    }
}