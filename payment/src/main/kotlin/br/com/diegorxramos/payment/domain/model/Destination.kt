package br.com.diegorxramos.payment.domain.model

data class Destination(
    val key: String?,
    val type: String?
) {

    fun valid() {
        if (this.key == null || this.key.isEmpty()) throw IllegalArgumentException("pix key is required")
        if (this.type == null || this.type.isEmpty()) throw IllegalArgumentException("pix key type is required")
    }
}
