package br.com.diegorxramos.payment.domain.model


import spock.lang.Specification

import java.time.LocalDate

class RecurrenceTest extends Specification {

    def "should"() {
        given:
            String
            String description = "abc"
            LocalDate createdAt = LocalDate.now()
            LocalDate paymentDate = LocalDate.now()
            String destination = "manuel@gmail.com"
            BigDecimal amount = new BigDecimal("60.0")
            LocalDate finalDate = LocalDate.now().plusYears(1)
        when:
            Recurrence recurrence = new Recurrence("SEMANAL", finalDate)
            Payment payment = new Payment(paymentDate, amount, description, createdAt, recurrence, destination)
        then:
            payment.recurrence.nextDate == LocalDate.now().plusWeeks(1)
    }
}
