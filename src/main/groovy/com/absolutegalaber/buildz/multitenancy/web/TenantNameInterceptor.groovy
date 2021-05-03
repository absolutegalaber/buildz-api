package com.absolutegalaber.buildz.multitenancy.web

import com.absolutegalaber.buildz.multitenancy.context.TenantContextStore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.servlet.HandlerInterceptor

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TenantNameInterceptor implements HandlerInterceptor {

    @Autowired
    TenantContextStore tenantContextStore

    boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) {
        String tenantName = request.getHeader("X-TenantName")

        println "interceptor put ${tenantName} in ${tenantContextStore}"
        // tenantContextStore.tenantUUID = tenantName

        true
    }

    @Override
    void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception exception
    ) {
        tenantContextStore.clear()
    }
}
