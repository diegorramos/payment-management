package br.com.diegorxramos.payment.domain.status

import br.com.diegorxramos.payment.domain.enum.PaymentStatus
import java.time.LocalDate

class InvalidClassification : PaymentClassification {

    override fun classify(date: LocalDate): PaymentStatus {
        throw IllegalArgumentException("invalid payment date")
    }
}