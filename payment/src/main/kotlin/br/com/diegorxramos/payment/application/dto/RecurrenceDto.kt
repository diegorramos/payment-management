package br.com.diegorxramos.payment.application.dto

import br.com.diegorxramos.payment.domain.enum.RecurrenceFrequency
import java.time.LocalDate

data class RecurrenceDto(
    val finalDate: LocalDate?,
    val frequency: RecurrenceFrequency?
)