package com.suri.multitenancy.components;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import static com.suri.multitenancy.utils.TenantConstants.CURRENT_TENANT_IDENTIFIER;
import static com.suri.multitenancy.utils.TenantConstants.DEFAULT_TENANT;

/**
 * The purpose of this class is to determine the current tenant for whom the processing will be done.
 * With annotation @Component, such classes are considered as candidates for auto-detection when using annotation-based
 * configuration and classpath scanning
 * Once the current tenant identifier is obtained from the request attribute, then multi tenant connection provider is
 * called in order to set the schema based on the tenant.
 */
@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

    public String resolveCurrentTenantIdentifier() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            String tenantId = (String) requestAttributes.getAttribute(CURRENT_TENANT_IDENTIFIER, RequestAttributes.SCOPE_REQUEST);
            if (tenantId != null) {
                return tenantId;
            }
        }
        return DEFAULT_TENANT;
    }

    public boolean validateExistingCurrentSessions() {
        return false;
    }
}
