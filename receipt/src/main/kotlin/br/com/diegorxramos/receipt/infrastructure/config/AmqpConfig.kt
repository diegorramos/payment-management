package br.com.diegorxramos.receipt.infrastructure.config

import br.com.diegorxramos.receipt.infrastructure.enum.Exchange
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AmqpConfig {

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
            binding(amqpAdmin, Exchange.PAYMENT_RECEIPT_QUEUE, Exchange.RECEIPT_EXCHANGE)
        }
    }

    private fun binding(amqpAdmin: AmqpAdmin, queueName: String, exchangeName: String) {
        val args: Map<String, Any> = mapOf("x-delayed-type" to "topic")
        val topic = TopicExchange(exchangeName, EXCHANGE_DURABLE, EXCHANGE_AUTO_DELETE, args)
        val queue = Queue(queueName, EXCHANGE_DURABLE)
        val binding = BindingBuilder.bind(queue).to(topic).with(exchangeName)

        amqpAdmin.declareQueue(queue)
        amqpAdmin.declareBinding(binding)
    }
}