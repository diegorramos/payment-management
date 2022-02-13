package br.com.diegorxramos.receipt.application.dto

import java.math.BigDecimal
import java.time.LocalDate

data class PaymentDto (
    val id: String?,
    val status: String?,
    val date: LocalDate?,
    val frequency: String?,
    val amount: BigDecimal?,
    val destination: String?,
    val description: String?,
    val nextDate: LocalDate?,
    val createdAt: LocalDate?,
    val finalDate: LocalDate?
)
