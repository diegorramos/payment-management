package br.com.diegorxramos.payment.application.service

import br.com.diegorxramos.payment.application.dto.PaymentDto
import br.com.diegorxramos.payment.application.exception.ConflictException
import br.com.diegorxramos.payment.application.service.PaymentService
import br.com.diegorxramos.payment.domain.model.Destination
import br.com.diegorxramos.payment.domain.model.Payment
import br.com.diegorxramos.payment.domain.model.Recurrence
import br.com.diegorxramos.payment.domain.repository.PaymentRepository
import br.com.diegorxramos.payment.fixture.PaymentDtoFixture
import br.com.diegorxramos.payment.infrastructure.repository.PaymentRepositoryImpl
import spock.lang.Specification

import java.time.LocalDate

class PaymentServiceTest extends Specification {

    PaymentService service
    PaymentRepository repository = new PaymentRepositoryImpl()

    void setup() {
        service = new PaymentService(repository)
    }

    def "must classify payment confirmed when payment date is current date"() {
        given:
            String description = "abc"
            LocalDate paymentDate = LocalDate.now()
            BigDecimal amount = new BigDecimal("2000")
            Destination destination = new Destination("abc", "abc")
            Recurrence recurrence = new Recurrence(LocalDate.now(), "abc")
            PaymentDto dto = new PaymentDto(paymentDate, amount, description, recurrence, destination)
        when:
            Payment payment = service.create(dto).block()
        then:
            payment.status.toString() == "CONFIRMED"
    }

    def "must classify payment scheduled when payment date is greater than from current date"() {
        given:
            String description = "abc"
            BigDecimal amount = new BigDecimal("2000")
            LocalDate paymentDate = LocalDate.now().plusDays(1)
            Destination destination = new Destination("abc", "abc")
            Recurrence recurrence = new Recurrence(LocalDate.now(), "abc")
            PaymentDto dto = new PaymentDto(paymentDate, amount, description, recurrence, destination)
        when:
            Payment payment = service.create(dto).block()
        then:
            payment.status.toString() == "SCHEDULED"
    }

    def "should return exception when payment date is less than from current date"() {
        given:
            String description = "abc"
            BigDecimal amount = new BigDecimal("2000")
            LocalDate paymentDate = LocalDate.now().minusDays(1)
            Destination destination = new Destination("abc", "abc")
            Recurrence recurrence = new Recurrence(LocalDate.now(), "")
            PaymentDto dto = new PaymentDto(paymentDate, amount, description, recurrence, destination)
        when:
            service.create(dto).block()
        then:
            thrown(IllegalArgumentException)
    }

    def "should return exception when exists payments with the same amount, date and destination"() {
        given:
            PaymentDto dto = PaymentDtoFixture.of(LocalDate.now(), new BigDecimal("2000"), "ABC")
            repository.create(new Payment(dto.date, dto.amount, dto.description, LocalDate.now(), dto.recurrence, dto.destination)).block()
        when:
            service.create(dto).block()
        then:
            thrown(ConflictException.class)
    }
}
