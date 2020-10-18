package com.absolutegalaber.buildz

import com.absolutegalaber.buildz.config.JerseyConfig
import com.absolutegalaber.buildz.config.LoggingConfig
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration

@SpringBootApplication(exclude = [ErrorMvcAutoConfiguration])
class BuildzApiApplication {

    static void main(String[] args) {
        SpringApplication.run([BuildzApiApplication, JerseyConfig, LoggingConfig] as Class[], args)
    }

}
