package com.erp.studentInformationSystem.config;

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

    // Getters & Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserRole() { return userRole; }
    public void setUserRole(String userRole) { this.userRole = userRole; }
}
