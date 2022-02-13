package br.com.diegorxramos.payment.application.service

import br.com.diegorxramos.payment.application.dto.PaymentDto
import br.com.diegorxramos.payment.application.exception.ConflictException
import br.com.diegorxramos.payment.domain.model.Payment
import br.com.diegorxramos.payment.domain.repository.PaymentRepository
import br.com.diegorxramos.payment.infrastructure.notification.PaymentSuccessNotification
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Specification

import java.time.LocalDate

class PaymentServiceTest extends Specification {

    PaymentService service
    PaymentRepository repository

    void setup() {
        repository = Mock(PaymentRepository)
        List<PaymentSuccessNotification> notifications = []
        service = new PaymentService(repository, notifications)
    }

    def "should not create payment when exists payment with destination and amount and date equals"() {
        given:
            String destination = "mario@gmail.com"
            LocalDate createdAt = LocalDate.now()
            LocalDate paymentDate = LocalDate.now()
            BigDecimal amount = new BigDecimal("2000.0")
            String description = "sdfjasldjflksadjgklsfhgksdfhgkfsdhg"
            PaymentDto dto = new PaymentDto(paymentDate, amount, description, destination, createdAt, null)
        when:
            1 * repository.findByAmountAndDateAndDestination(paymentDate, amount, destination) >> {
                Mono.just(new Payment(null, paymentDate, amount, description, createdAt, null, null, destination))
            }
        then:
            StepVerifier.create(service.create(dto))
                    .expectError(ConflictException.class)
                    .verify()
    }

    def "should create payment when not exists payment with destination and amount and date equals"() {
        given:
            String destination = "mario@gmail.com"
            LocalDate createdAt = LocalDate.now()
            LocalDate paymentDate = LocalDate.now()
            BigDecimal amount = new BigDecimal("2000.0")
            String description = "sdfjasldjflksadjgklsfhgksdfhgkfsdhg"
            PaymentDto dto = new PaymentDto(paymentDate, amount, description, destination, createdAt, null)
        when:
            1 * repository.findByAmountAndDateAndDestination(paymentDate, amount, destination) >> Mono.empty()
            1 * repository.create(*_) >> Mono.empty()
        then:
            StepVerifier.create(service.create(dto))
                    .verifyComplete()
    }
}
