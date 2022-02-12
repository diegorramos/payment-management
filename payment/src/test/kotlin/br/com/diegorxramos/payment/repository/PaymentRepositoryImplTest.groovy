package br.com.diegorxramos.payment.repository


import br.com.diegorxramos.payment.domain.model.Destination
import br.com.diegorxramos.payment.domain.model.Payment
import br.com.diegorxramos.payment.domain.model.Recurrence
import br.com.diegorxramos.payment.infrastructure.repository.PaymentRepositoryImpl
import spock.lang.Specification

import java.time.LocalDate

class PaymentRepositoryImplTest extends Specification {

    PaymentRepositoryImpl repository

    void setup() {
        repository = new PaymentRepositoryImpl()
    }

    def "should create payment"() {
        given:
            String description = "abc"
            LocalDate paymentDate = LocalDate.now()
            BigDecimal amount = new BigDecimal("2000")
            Destination destination = new Destination("abc", "abc")
            Recurrence recurrence = new Recurrence(LocalDate.now(), "")
            Payment payment = new Payment(paymentDate, amount, description, LocalDate.now(), recurrence, destination)
        when:
            repository.create(payment).block()
        then:
            List<Payment> payments = repository.list().collectList().block()
            payments.size() == 1

    }
}
