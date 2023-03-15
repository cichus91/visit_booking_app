package pl.spjava.gabinet.web.account;


import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.spjava.gabinet.exception.AppBaseException;

import java.io.Serializable;

@Named
@RequestScoped
public class ChangeMyPasswordBean implements Serializable {

    @Inject
    private AccountController accountController;

    private String passwordToChange;

    private String confirmPasswordToChange;

    private String confirmPassword = "";

    private String newPassword;
    @PostConstruct
    private void init(){
        passwordToChange = accountController.loadMyPasswordToChange();
    }

    public String getPasswordToChange() {
        return passwordToChange;
    }

    public void setPasswordToChange(String passwordToChange) {
        this.passwordToChange = passwordToChange;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPasswordToChange() {
        return confirmPasswordToChange;
    }

    public void setConfirmPasswordToChange(String confirmPasswordToChange) {
        this.confirmPasswordToChange = confirmPasswordToChange;
    }

    public String confirmPasswordChange() throws AppBaseException {
        if (!passwordToChange.equals(confirmPasswordToChange) && !confirmPassword.equals(newPassword) && passwordToChange.equals(newPassword)){
            return null;
        }
        return accountController.confirmMyPasswordChange(newPassword);
    }
}
