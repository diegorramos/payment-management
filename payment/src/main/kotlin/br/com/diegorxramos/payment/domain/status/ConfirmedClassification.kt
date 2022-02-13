package br.com.diegorxramos.payment.domain.status

import br.com.diegorxramos.payment.domain.enum.PaymentStatus
import java.time.LocalDate

class ConfirmedClassification(val next: PaymentClassification) : PaymentClassification {

    override fun classify(date: LocalDate): PaymentStatus {
        if (date.compareTo(LocalDate.now()) == 0) {
            return PaymentStatus.CONFIRMED
        }
        return next.classify(date)
    }
}