package br.com.diegorxramos.payment.fixture


import br.com.diegorxramos.payment.domain.model.Destination
import br.com.diegorxramos.payment.domain.model.Payment
import br.com.diegorxramos.payment.domain.model.Recurrence

import java.time.LocalDate

class PaymentFixture {

    static Payment of(LocalDate date, BigDecimal amount, String destinationKey) {
        String description = "abc"
        Destination destination = new Destination(destinationKey, "abc")
        Recurrence recurrence = new Recurrence(LocalDate.now(), "")
        new Payment(date, amount, description, LocalDate.now(), recurrence, destination)
    }
}
