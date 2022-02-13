package br.com.diegorxramos.receipt.infrastructure.config

import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate

@Configuration
class DataSourceConfig {

    @Bean
    fun reactiveTemplate(connectionFactory: ConnectionFactory) = R2dbcEntityTemplate(connectionFactory)
}