package com.absolutegalaber.buildz.multitenancy.config

import com.absolutegalaber.buildz.multitenancy.context.TenantContextStore
import com.absolutegalaber.buildz.multitenancy.context.TenantFilter
import com.absolutegalaber.buildz.multitenancy.web.TenantNameInterceptor
import org.springframework.aop.framework.ProxyFactoryBean
import org.springframework.aop.target.ThreadLocalTargetSource
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Conditional
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Scope
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

import javax.servlet.Filter

@Configuration
@Conditional(MultitenancyEnabledCondition.class)
class TenantContextConfig implements WebMvcConfigurer {

    @Bean
    Filter tenantFilter() {
        new TenantFilter()
    }

    @Bean
    FilterRegistrationBean tenantFilterRegistration() {
        FilterRegistrationBean result = new FilterRegistrationBean()

        result.filter = this.tenantFilter()
        result.urlPatterns = ["/*"]
        result.name = "Tenant Store Filter"
        result.order = 1

        return result
    }

    @Bean
    TenantNameInterceptor tenantNameInterceptor() {
        new TenantNameInterceptor()
    }

    @Bean(destroyMethod = "destroy")
    ThreadLocalTargetSource threadLocalTenantStore() {
        ThreadLocalTargetSource result = new ThreadLocalTargetSource()

        result.targetBeanName = "tenantContextStore"

        result
    }

    @Primary
    @Bean(name = "proxiedThreadLocalTargetSource")
    ProxyFactoryBean proxiedThreadLocalTargetSource(ThreadLocalTargetSource threadLocalTargetSource) {
        ProxyFactoryBean result = new ProxyFactoryBean()

        result.targetSource = threadLocalTargetSource

        result
    }

    @Bean
    @Scope(scopeName = "prototype")
    TenantContextStore tenantContextStore() {
        new TenantContextStore()
    }

    @Override
    void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.tenantNameInterceptor())
    }
}
