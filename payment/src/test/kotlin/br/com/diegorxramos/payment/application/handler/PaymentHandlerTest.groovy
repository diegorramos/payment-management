package br.com.diegorxramos.payment.application.handler

import br.com.diegorxramos.payment.application.dto.PaymentDto
import br.com.diegorxramos.payment.domain.model.Payment
import br.com.diegorxramos.payment.domain.repository.PaymentRepository
import br.com.diegorxramos.payment.fixture.PaymentDtoFixture
import br.com.diegorxramos.payment.fixture.PaymentFixture
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import spock.lang.Specification

import java.time.LocalDate

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaymentHandlerTest extends Specification {

    @Autowired
    private PaymentRepository repository
    @Autowired
    private WebTestClient webTestClient

    void cleanup() {
        repository.delete()
    }

    def "should return payment list empty"() {
        when:
            def response = webTestClient
                    .get()
                    .uri("/payments")
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBody(List<Payment>.class)
        then:
            response.value { payments ->
                assert payments.isEmpty()
            }
    }

    def "should return valid payment list"() {
        given:
            Payment payment = PaymentFixture.of(LocalDate.now(), new BigDecimal("2000"), UUID.randomUUID().toString())
        when:
            repository.create(payment)
        then:
            webTestClient
                    .get()
                    .uri("/payments")
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBody(List<Payment>.class)
                    .value { payments ->
                        assert payments.size() == 1
                    }
    }

    def "should create payment with random pix key"() {
        given:
            PaymentDto payment = PaymentDtoFixture.of(LocalDate.now(), new BigDecimal("2000"), UUID.randomUUID().toString())
        expect:
            webTestClient
                    .post()
                    .uri("/payments")
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(payment))
                    .exchange()
                    .expectStatus()
                    .isCreated()
    }

    def "should return exception when try create payment without payment date"() {
        given:
            PaymentDto payment = PaymentDtoFixture.of(null, new BigDecimal("2000"), UUID.randomUUID().toString())
        expect:
            webTestClient
                    .post()
                    .uri("/payments")
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(payment))
                    .exchange()
                    .expectStatus()
                    .isBadRequest()
    }

    def "should return conflict status code when exists payments with the same amount, date and destination"() {
        given:
            PaymentDto dto = PaymentDtoFixture.of(LocalDate.now(), new BigDecimal("2000"), UUID.randomUUID().toString())
            Payment payment = PaymentFixture.of(dto.date, dto.amount, dto.destination.key)
        when:
            repository.create(payment)
        then:
            webTestClient
                    .post()
                    .uri("/payments")
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(dto))
                    .exchange()
                    .expectStatus()
                    .isEqualTo(HttpStatus.CONFLICT)
    }

    def "must return only payments confirmed"() {
        given:
            Payment paymentConfirmed = PaymentFixture.of(LocalDate.now(), new BigDecimal("2000"), UUID.randomUUID().toString())
            Payment paymentScheduled = PaymentFixture.of(LocalDate.now().plusDays(1), new BigDecimal("2000"), UUID.randomUUID().toString())
        when:
            repository.create(paymentConfirmed)
            repository.create(paymentScheduled)
        then:
            webTestClient
                    .get()
                    .uri("/payments/confirmed")
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBody(List<Payment>.class)
                    .value { List<Payment> payments ->
                        assert payments.size() == 1
                        assert payments[0].status.toString() == "CONFIRMED"
                    }
    }

    def "must return only payments schedule"() {
        given:
            Payment paymentConfirmed = PaymentFixture.of(LocalDate.now(), new BigDecimal("2000"), UUID.randomUUID().toString())
            Payment paymentScheduled = PaymentFixture.of(LocalDate.now().plusDays(1), new BigDecimal("2000"), UUID.randomUUID().toString())
        when:
            repository.create(paymentConfirmed)
            repository.create(paymentScheduled)
        then:
            webTestClient
                    .get()
                    .uri("/payments/scheduled")
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBody(List<Payment>.class)
                    .value { List<Payment> payments ->
                        assert payments.size() == 1
                        assert payments[0].status.toString() == "SCHEDULED"
                    }
    }
}
