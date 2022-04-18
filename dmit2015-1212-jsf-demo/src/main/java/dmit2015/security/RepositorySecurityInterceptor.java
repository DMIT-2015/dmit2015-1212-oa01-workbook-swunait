package dmit2015.security;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;

public class RepositorySecurityInterceptor {

    @Inject
    private Security _security;


    @AroundInvoke
    private Object authorize(InvocationContext ic) throws Exception {
        String methodName = ic.getMethod().getName();
        if (methodName.startsWith("create")) {
            // Check if the user has been authenticated
            if (!_security.isAuthenticated()) {
                throw new RuntimeException("Access denied. Anonymonus users do not have permission to access this method.");
            }
            boolean hasPermission = _security.isInAnyRole("Sales");
            if (!hasPermission) {
                throw new RuntimeException("Access denied. Your role does not allow you to access this method.");
            }
        }
        return ic.proceed();
    }

}
