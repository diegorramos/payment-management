package br.com.diegorxramos.payment.domain.status

import br.com.diegorxramos.payment.domain.enum.PaymentStatus
import java.time.LocalDate
import kotlin.jvm.Throws

interface PaymentClassification {

    @Throws(IllegalArgumentException::class)
    fun classify(date: LocalDate): PaymentStatus
}