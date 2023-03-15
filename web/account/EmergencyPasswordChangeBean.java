package pl.spjava.gabinet.web.account;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import pl.spjava.gabinet.exception.AppBaseException;

import java.io.Serializable;
import java.util.Random;

@Named
@ViewScoped
public class EmergencyPasswordChangeBean implements Serializable {

    @Inject
    private AccountController accountController;

    private String newPassword = "";

    @PostConstruct
    public void init(){
        newPassword = generateRandomPassword();
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String confirmPasswordChange() throws AppBaseException {
        return accountController.confirmEmergencyPasswordChange(newPassword);
    }

    private static String generateRandomPassword(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 8;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
         return generatedString;
    }


}
