package br.com.diegorxramos.payment.domain.model


import spock.lang.Specification

import java.time.LocalDate

class PaymentTest extends Specification {

    def "must classify payment confirmed when payment date is current date"() {
        given:
            String description = "abc"
            LocalDate paymentDate = LocalDate.now()
            BigDecimal amount = new BigDecimal("2000")
            LocalDate createdAt = LocalDate.now()
        when:
            Payment payment = new Payment(paymentDate, amount, description, createdAt, null, null)
        then:
            payment.status.toString() == "CONFIRMED"
    }

    def "must classify payment scheduled when payment date is greater than from current date"() {
        given:
            String description = "abc"
            BigDecimal amount = new BigDecimal("2000")
            LocalDate createdAt = LocalDate.now()
            LocalDate paymentDate = LocalDate.now().plusDays(1)
        when:
            Payment payment = new Payment(paymentDate, amount, description, createdAt, null, null)
        then:
            payment.status.toString() == "SCHEDULED"
    }

    def "should return exception when payment date is less than from current date"() {
        given:
            String description = "abc"
            BigDecimal amount = new BigDecimal("2000")
            LocalDate createdAt = LocalDate.now()
            LocalDate paymentDate = LocalDate.now().minusDays(1)
        when:
            new Payment(paymentDate, amount, description, createdAt, null, null)
        then:
            thrown(IllegalArgumentException)
    }
}
