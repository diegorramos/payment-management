package br.com.diegorxramos.receipt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReceiptApplication

fun main(args: Array<String>) {
	runApplication<ReceiptApplication>(*args)
}
