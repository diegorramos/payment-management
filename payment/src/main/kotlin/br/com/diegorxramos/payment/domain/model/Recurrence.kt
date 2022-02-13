package br.com.diegorxramos.payment.domain.model

import java.time.LocalDate

data class Recurrence(
    val frequency: String?,
    val nextDate: LocalDate?,
    val finalDate: LocalDate?
)
