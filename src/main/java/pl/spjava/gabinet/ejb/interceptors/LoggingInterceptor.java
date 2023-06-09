package pl.spjava.gabinet.ejb.interceptors;


import jakarta.annotation.Resource;
import jakarta.ejb.SessionContext;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingInterceptor {

    @Resource
    private SessionContext sessionContext;

    @AroundInvoke
    public Object additionalInvokeForMethod(InvocationContext invocation) throws Exception {
        StringBuilder sb = new StringBuilder("Wywołanie metody biznesowej ")
                .append(invocation.getTarget().getClass().getName()).append('.')
                .append(invocation.getMethod().getName());
        sb.append(" z tożsamością: ").append(sessionContext.getCallerPrincipal().getName());
        try {
            Object[] parameters = invocation.getParameters();
            if (null != parameters) {
                for (Object param : parameters) {
                    if (param != null) {
                        sb.append(" z parametrem ").append(param.getClass().getName()).append('=').append(param);
                    } else {
                        sb.append(" bez wartości (null)");
                    }
                }
            }

            Object result = invocation.proceed();

            if (result != null) {
                sb.append(" zwrócono ").append(result.getClass().getName()).append('=').append(result);
            } else {
                sb.append(" zwrócono wartość null");
            }

            return result;
        } catch (Exception ex) {
            sb.append(" wystapil wyjatek: ").append(ex);
            throw ex; //ponowne zgloszenie wyjatku
        } finally {
            Logger.getGlobal().log(Level.INFO, sb.toString());
        }
    }
}
