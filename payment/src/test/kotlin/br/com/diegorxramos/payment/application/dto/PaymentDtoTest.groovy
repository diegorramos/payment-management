package br.com.diegorxramos.payment.application.dto


import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class PaymentDtoTest extends Specification {

    @Unroll
    def "should valid required parameters date=#date, amount=#amount, destination=#destination"() {
        given:
            String description = "abc"
            String destination = "diegorxramos@gmail.com"
        when:
            PaymentDto payment = new PaymentDto(date, amount, description, destination, createdAt, null)
            payment.valid()
        then:
            thrown(IllegalArgumentException.class)
        where:
            createdAt       | date            | amount
            null            | LocalDate.now() | new BigDecimal("10.0")
            LocalDate.now() | null            | new BigDecimal("10.0")
            LocalDate.now() | LocalDate.now() | null
    }

    def "should return exception when destination key is not email"() {
        given:
            String description = "abc"
            String destination = "mario"
            BigDecimal amount = new BigDecimal("2000")
            LocalDate createdAt = LocalDate.now()
            LocalDate paymentDate = LocalDate.now()
        when:
            def paymentDto = new PaymentDto(paymentDate, amount, description, destination, createdAt, null)
            paymentDto.valid()
        then:
            thrown(IllegalArgumentException)
    }

    def "should return exception when destination key is not cpf"() {
        given:
            String description = "abc"
            String destination = "222.222.2.2"
            BigDecimal amount = new BigDecimal("2000")
            LocalDate createdAt = LocalDate.now()
            LocalDate paymentDate = LocalDate.now()
        when:
            def paymentDto = new PaymentDto(paymentDate, amount, description, destination, createdAt, null)
            paymentDto.valid()
        then:
            thrown(IllegalArgumentException)
    }

    def "should not return exception when destination key is email"() {
        given:
            String description = "abc"
            String destination = "mario@gmail.com"
            BigDecimal amount = new BigDecimal("2000")
            LocalDate createdAt = LocalDate.now()
            LocalDate paymentDate = LocalDate.now()
        when:
            def paymentDto = new PaymentDto(paymentDate, amount, description, destination, createdAt, null)
            paymentDto.valid()
        then:
            notThrown(IllegalArgumentException)
    }

    def "should not return exception when destination key is cpf"() {
        given:
            String description = "abc"
            String destination = "222.222.222-45"
            BigDecimal amount = new BigDecimal("2000")
            LocalDate createdAt = LocalDate.now()
            LocalDate paymentDate = LocalDate.now()
        when:
            def paymentDto = new PaymentDto(paymentDate, amount, description, destination, createdAt, null)
            paymentDto.valid()
        then:
            notThrown(IllegalArgumentException)
    }

    def "should return exception when destination key is null"() {
        given:
            String description = "abc"
            String destination = null
            BigDecimal amount = new BigDecimal("2000")
            LocalDate createdAt = LocalDate.now()
            LocalDate paymentDate = LocalDate.now()
        when:
            def paymentDto = new PaymentDto(paymentDate, amount, description, destination, createdAt, null)
            paymentDto.valid()
        then:
            thrown(IllegalArgumentException)
    }

    def "should return exception when destination key is empty"() {
        given:
            String description = "abc"
            String destination = ""
            BigDecimal amount = new BigDecimal("2000")
            LocalDate createdAt = LocalDate.now()
            LocalDate paymentDate = LocalDate.now()
        when:
            def paymentDto = new PaymentDto(paymentDate, amount, description, destination, createdAt,  null)
            paymentDto.valid()
        then:
            thrown(IllegalArgumentException)
    }

    def "should not return exception when destination key is uuid v4"() {
        given:
            String description = "abc"
            String destination = UUID.randomUUID().toString()
            BigDecimal amount = new BigDecimal("2000")
            LocalDate createdAt = LocalDate.now()
            LocalDate paymentDate = LocalDate.now()
        when:
            def paymentDto = new PaymentDto(paymentDate, amount, description, destination, createdAt, null)
            paymentDto.valid()
        then:
            notThrown(IllegalArgumentException)
    }
}
