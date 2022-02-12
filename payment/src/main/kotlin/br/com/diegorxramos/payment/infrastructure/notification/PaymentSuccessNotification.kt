package br.com.diegorxramos.payment.infrastructure.notification

import br.com.diegorxramos.payment.domain.model.Payment

interface PaymentSuccessNotification {

    fun notify(payment: Payment)
}