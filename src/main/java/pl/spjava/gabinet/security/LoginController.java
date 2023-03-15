package pl.spjava.gabinet.security;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.Password;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import pl.spjava.gabinet.dto.AccountDTO;
import pl.spjava.gabinet.model.Account;
import pl.spjava.gabinet.utils.ContextUtils;


@Named
@RequestScoped
public class LoginController {

    @NotNull
    private String userName;

    @NotNull
    private String password;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private FacesContext facesContext;

    @Inject
    private HashGenerator hashGenerator;

    public String login() {
        Credential credential = new UsernamePasswordCredential(userName, new Password(password));
        AuthenticationStatus authenticationStatus = securityContext.authenticate(getRequest(), getResponse(), AuthenticationParameters.withParams().credential(credential));
        if(authenticationStatus.equals(AuthenticationStatus.SEND_CONTINUE)){
            facesContext.responseComplete();
            return "";
        } else if (authenticationStatus.equals(AuthenticationStatus.SEND_FAILURE)){
            return "loginerror";
        }
        return "index";
    }

    private HttpServletRequest getRequest(){
        return (HttpServletRequest) facesContext.getExternalContext().getRequest();
    }

    private HttpServletResponse getResponse(){
        return (HttpServletResponse) facesContext.getExternalContext().getResponse();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = hashGenerator.generateHash(password);
    }
}
