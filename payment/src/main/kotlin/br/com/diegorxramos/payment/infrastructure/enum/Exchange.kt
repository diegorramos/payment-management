package br.com.diegorxramos.payment.infrastructure.enum

object Exchange {

    const val RECEIPT_EXCHANGE = "payment.receipt.send"
    const val PAYMENT_CREATED_EXCHANGE = "payment.created"
    const val PAYMENT_UPDATED_EXCHANGE = "payment.updated"
    const val PAYMENT_DELETED_EXCHANGE = "payment.deleted"
}