package dmit2015.security;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;

public class CallerUserSecurityInterceptor {

    @Inject
    private Security _security;

    @AroundInvoke
    public Object verifyAccess(InvocationContext ic) throws Exception {
        String methodName = ic.getMethod().getName();

//        if (!_security.isAuthenticated()) {
//            throw new RuntimeException("Access denied. You do not have access to this method");
//        }

//        if (methodName.toLowerCase().startsWith("add") || methodName.toLowerCase().startsWith("update") || methodName.toLowerCase().startsWith("delete")) {
//            if (!_security.isInAnyRole("DEVELOPER","ADMIN")) {
//                throw new RuntimeException("Access denied! You do not have permission to execute this method.");
//            }
//        }

        return ic.proceed();
    }
}