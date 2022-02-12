package br.com.diegorxramos.payment.domain.model

import java.time.LocalDate

data class Recurrence(
    val date: LocalDate?,
    val frequency: String?
) {

    fun valid() {
        if (this.date == null) throw IllegalArgumentException("recurrence data is required")
        if (this.frequency == null || this.frequency.isEmpty()) throw IllegalArgumentException("recurrence frequency is required")
    }
}
