package br.com.diegorxramos.payment.application.dto

import java.time.LocalDate

data class RecurrenceDto(
    var frequency: String? = null,
    var finalDate: LocalDate? = null
)