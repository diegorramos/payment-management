package br.com.diegorxramos.payment.infrastructure.config

import br.com.diegorxramos.payment.infrastructure.enum.Exchange
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AmqpConfig(private val amqpTemplate: RabbitMessagingTemplate) {

    companion object {
        const val EXCHANGE_DURABLE = true
        const val EXCHANGE_AUTO_DELETE = false
    }

    @Bean
    fun rabbitListenerContainerFactory(connectionFactory: ConnectionFactory): SimpleRabbitListenerContainerFactory {
        return SimpleRabbitListenerContainerFactory().apply {
            setConnectionFactory(connectionFactory)
            setDefaultRequeueRejected(false)
            setMissingQueuesFatal(false)
            setConcurrentConsumers(2)
            setMaxConcurrentConsumers(10)
            setPrefetchCount(1)
        }
    }

    @Bean
    fun setupQueues(amqpAdmin: AmqpAdmin): InitializingBean {
        return InitializingBean {
            declareTopic(amqpAdmin, Exchange.RECEIPT_EXCHANGE)
            declareTopic(amqpAdmin, Exchange.PAYMENT_CREATED_EXCHANGE)
            declareTopic(amqpAdmin, Exchange.PAYMENT_UPDATED_EXCHANGE)
            declareTopic(amqpAdmin, Exchange.PAYMENT_DELETED_EXCHANGE)
        }
    }

    private fun declareTopic(amqpAdmin: AmqpAdmin, exchangeName: String){
        val args: Map<String, Any> = mapOf("x-delayed-type" to "topic")
        val customExchange = TopicExchange(exchangeName, EXCHANGE_DURABLE, EXCHANGE_AUTO_DELETE, args)
        amqpAdmin.declareExchange(customExchange)
    }
}