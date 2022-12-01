package it.codeland.academy.core.utils;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * resource resolver factory helper class
 */
public final class ResolverUtil {

    public ResolverUtil() {

    }

    public static final String USER = "unicreditserviceuser";

    /**
     * @param resourceResolverFactory factory
     * @return new resource resolver for service user
     * @throws LoginException error exception
     */
    public ResourceResolver getResolver(ResourceResolverFactory resourceResolverFactory) throws LoginException {
        final Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put(ResourceResolverFactory.SUBSERVICE, USER);

        ResourceResolver resolver = resourceResolverFactory.getServiceResourceResolver(paramMap);
        return resolver;
    }

}