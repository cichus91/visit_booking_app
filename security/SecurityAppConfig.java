package pl.spjava.gabinet.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.annotation.FacesConfig;
import jakarta.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import jakarta.security.enterprise.authentication.mechanism.http.LoginToContinue;

@FacesConfig
@ApplicationScoped
@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/faces/account/loginform.xhtml",
                errorPage = "/faces/error/loginerror.xhtml",
                useForwardToLogin = false
        )
)
public class SecurityAppConfig {
}
