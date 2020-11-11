package com.absolutegalaber.buildz.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.view.RedirectView

@Controller
class WelcomeController {
    @GetMapping('/')
    index() {
        return new RedirectView('/swagger-ui/')
    }
}
