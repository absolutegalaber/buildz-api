package com.absolutegalaber.buildz.config

import com.absolutegalaber.buildz.aop.LoggingAspect
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy

@Configuration
@EnableAspectJAutoProxy
class LoggingConfig {
    @Bean
    LoggingAspect loggingAspect() {
        return new LoggingAspect()
    }
}
