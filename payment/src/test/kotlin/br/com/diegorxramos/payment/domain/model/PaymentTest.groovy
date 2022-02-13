package br.com.diegorxramos.payment.domain.model

import br.com.diegorxramos.payment.domain.status.PaymentClassificationStatus
import spock.lang.Specification

import java.time.LocalDate

class PaymentTest extends Specification {

    def "must classify payment confirmed when payment date is current date"() {
        given:
            LocalDate paymentDate = LocalDate.now()
        when:
            def status = new PaymentClassificationStatus().classify(paymentDate)
        then:
            status.toString() == "CONFIRMED"
    }

    def "must classify payment scheduled when payment date is greater than from current date"() {
        given:
            LocalDate paymentDate = LocalDate.now().plusDays(1)
        when:
            def status = new PaymentClassificationStatus().classify(paymentDate)
        then:
            status.toString() == "SCHEDULED"
    }

    def "should return exception when payment date is less than from current date"() {
        given:
            LocalDate paymentDate = LocalDate.now().minusDays(1)
        when:
            new PaymentClassificationStatus().classify(paymentDate)
        then:
            thrown(Exception.class)
    }
}
