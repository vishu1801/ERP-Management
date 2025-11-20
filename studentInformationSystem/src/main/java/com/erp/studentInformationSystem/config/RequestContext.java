package com.erp.studentInformationSystem.config;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestContext {

    private static final ThreadLocal<RequestContext> userContext = ThreadLocal.withInitial(RequestContext::new);

    private String userId;
    private String userRole;

    public static RequestContext getCurrentContext() {
        return userContext.get();
    }

    public static void clear() {
        userContext.remove();
    }

}
