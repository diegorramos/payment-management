package br.com.diegorxramos.payment.domain.model

import br.com.diegorxramos.payment.domain.recurrence.RecurrenceFrequencies
import spock.lang.Specification

import java.time.LocalDate

class RecurrenceTest extends Specification {

    def "must weekly when payment amount to greater than 50 and recurrence final date less then 12 months"() {
        given:
            String
            BigDecimal amount = new BigDecimal("60.0")
            LocalDate finalDate = LocalDate.now().plusYears(1)
        when:
            LocalDate next = new RecurrenceFrequencies().calc(amount, finalDate, "WEEKLY")
        then:
            next
            next == LocalDate.now().plusWeeks(1)
    }

    def "must monthly when payment amount to greater than 100 and recurrence final date less then 24 months"() {
        given:
            String
            BigDecimal amount = new BigDecimal("110.0")
            LocalDate finalDate = LocalDate.now().plusYears(1)
        when:
            LocalDate next = new RecurrenceFrequencies().calc(amount, finalDate, "MONTHLY")
        then:
            next
            next == LocalDate.now().plusMonths(1)
    }

    def "must quarterly when payment amount to greater than 130 and recurrence final date less then 36 months"() {
        given:
            String
            BigDecimal amount = new BigDecimal("140.0")
            LocalDate finalDate = LocalDate.now().plusYears(1)
        when:
            LocalDate next = new RecurrenceFrequencies().calc(amount, finalDate, "QUARTERLY")
        then:
            next
            next == LocalDate.now().plusMonths(3)
    }

    def "must semiannual when payment amount to greater than 150 and recurrence final date less then 42 months"() {
        given:
            String
            BigDecimal amount = new BigDecimal("160.0")
            LocalDate finalDate = LocalDate.now().plusYears(1)
        when:
            LocalDate next = new RecurrenceFrequencies().calc(amount, finalDate, "SEMIANNUAL")
        then:
            next
            next == LocalDate.now().plusMonths(6)
    }
}
