package br.com.diegorxramos.payment.fixture

import br.com.diegorxramos.payment.application.dto.PaymentDto
import br.com.diegorxramos.payment.domain.model.Destination
import br.com.diegorxramos.payment.domain.model.Recurrence

import java.time.LocalDate

class PaymentDtoFixture {

    static PaymentDto of() {
        String description = "abc"
        LocalDate paymentDate = LocalDate.now()
        BigDecimal amount = new BigDecimal("2000")
        Destination destination = new Destination("abc", "abc")
        Recurrence recurrence = new Recurrence(LocalDate.now(), "")
        new PaymentDto(paymentDate, amount, description, recurrence, destination)
    }

    static PaymentDto of(LocalDate date, BigDecimal amount, String destinationKey) {
        String description = "abc"
        Destination destination = new Destination(destinationKey, "abc")
        Recurrence recurrence = new Recurrence(LocalDate.now(), "abc")
        new PaymentDto(date, amount, description, recurrence, destination)
    }
}
