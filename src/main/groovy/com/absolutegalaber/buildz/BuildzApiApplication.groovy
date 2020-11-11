package com.absolutegalaber.buildz

import com.absolutegalaber.buildz.config.BuildzApiDocsConfig
import com.absolutegalaber.buildz.config.LoggingConfig
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Import

@SpringBootApplication()
@Import([LoggingConfig, BuildzApiDocsConfig])
class BuildzApiApplication {

    static void main(String[] args) {
        SpringApplication.run(BuildzApiApplication, args)
    }

}
