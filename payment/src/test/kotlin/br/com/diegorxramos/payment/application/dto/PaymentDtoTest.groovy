package br.com.diegorxramos.payment.application.dto

import br.com.diegorxramos.payment.domain.model.Destination
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class PaymentDtoTest extends Specification {

    @Unroll
    def "should valid required parameters date=#date, amount=#amount, destination=#destination"() {
        given:
            String description = "abc"
        when:
            PaymentDto payment = new PaymentDto(date, amount, description, recurrenceData, paymentDestination)
            payment.valid()
        then:
            thrown(IllegalArgumentException.class)
        where:
            createdAt       | date            | amount                 | recurrenceData | paymentDestination
            LocalDate.now() | LocalDate.now() | new BigDecimal("10.0") | null           | null
            null            | LocalDate.now() | new BigDecimal("10.0") | null           | null
            LocalDate.now() | null            | new BigDecimal("10.0") | null           | null
            LocalDate.now() | LocalDate.now() | null                   | null           | null
            LocalDate.now() | LocalDate.now() | new BigDecimal("10.0") | null           | null
            LocalDate.now() | LocalDate.now() | new BigDecimal("10.0") | null           | new Destination("", "")
    }
}
