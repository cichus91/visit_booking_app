package pl.spjava.gabinet.web.account;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.spjava.gabinet.dto.AccountDTO;
import pl.spjava.gabinet.dto.AddressDTO;
import pl.spjava.gabinet.ejb.endpoints.AccountEndpoint;
import pl.spjava.gabinet.exception.AppBaseException;

import java.io.Serializable;
import java.util.logging.Logger;

@RequestScoped
@Named
public class CreateAccountBean implements Serializable {

    @EJB
    private AccountEndpoint accountEndpoint;

    @Inject
    private AccountController accountController;

    private String repeatPassword = "";
    private AccountDTO accountDTO = new AccountDTO();
    private AddressDTO addressDTO = new AddressDTO();
    private static Logger LOG = Logger.getLogger(CreateAccountBean.class.getName());

    public AccountDTO getAccountDTO() {
        return accountDTO;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String createPatientAccount() throws AppBaseException{
        accountDTO.setAddressDTO(addressDTO);
        if(!(repeatPassword.equals((accountDTO.getPassword())))) {
            return null;
        }
        return accountController.confirmAccountCreation(accountDTO, accountDTO.getAddressDTO());
    }

    public String createReceptionistAccount(){
        return accountController.createNewReceptionist(accountDTO);
    }

    public String createAdminAccount() throws AppBaseException{
        return accountController.createNewAdmin(accountDTO);
    }


}
