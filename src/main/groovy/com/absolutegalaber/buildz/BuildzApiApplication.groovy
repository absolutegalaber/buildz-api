package com.absolutegalaber.buildz

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class BuildzApiApplication {

    static void main(String[] args) {
        SpringApplication.run([BuildzApiApplication, JerseyConfig] as Class[], args)
    }

}
