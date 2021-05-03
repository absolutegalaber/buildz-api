package com.absolutegalaber.buildz.multitenancy.context

import org.springframework.beans.factory.annotation.Autowired

import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class TenantFilter implements Filter {

    @Autowired
    TenantContextStore tenantContextStore

    @Override
    void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain
    ) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String tenantId = request.getHeader("X-TenantName");
        try {
            println "filter putting ${tenantId} in ${tenantContextStore}"
            this.tenantContextStore.setTenantUUID(tenantId);
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            // Otherwise when a previously used container thread is used, it will have the old tenant id set and
            // if for some reason this filter is skipped, tenantStore will hold an unreliable value
            this.tenantContextStore.clear();
        }
    }
}
