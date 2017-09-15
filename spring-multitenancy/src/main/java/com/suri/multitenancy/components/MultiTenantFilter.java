package com.suri.multitenancy.components;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.suri.multitenancy.utils.TenantConstants.CURRENT_TENANT_IDENTIFIER;
import static com.suri.multitenancy.utils.TenantConstants.DEFAULT_TENANT;

/**
 * Class is used to obtain the value of the tenant Id from the incoming request. Once obtained,
 * Current Tenant Identifier Resolver is called to obtain the value of tenant assigned here as the "current tenant".
 */
public class MultiTenantFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (request.getRequestURI().split("/")[2] != null) {
            request.setAttribute(CURRENT_TENANT_IDENTIFIER, request.getRequestURI().split("/")[2]);
        } else {
            request.setAttribute(CURRENT_TENANT_IDENTIFIER, DEFAULT_TENANT);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }
}
