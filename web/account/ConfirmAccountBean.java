package pl.spjava.gabinet.web.account;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.spjava.gabinet.dto.AccountDTO;
import pl.spjava.gabinet.dto.AddressDTO;
import pl.spjava.gabinet.ejb.endpoints.AccountEndpoint;

import java.io.Serializable;


@RequestScoped
@Named
public class ConfirmAccountBean implements Serializable {

    @EJB
    private AccountEndpoint accountEndpoint;

    @Inject
    private AccountController accountController;

    private AccountDTO accountDTO;

    private AddressDTO addressDTO;

    @PostConstruct
    public void init(){
        this.accountDTO = accountController.getConfirmAccountDTO();
    }

    public AccountDTO getAccountDTO() {
        return accountDTO;
    }

    public void setAccountDTO(AccountDTO accountDTO) {
        this.accountDTO = accountDTO;
    }

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    public void setAddressDTO(AddressDTO addressDTO) {
        this.addressDTO = addressDTO;
    }

    public String confirmAccount(){
//        accountDTO.setAddressDTO(addressDTO);
        return accountController.createNewPatient(accountDTO, accountDTO.getAddressDTO());
    }
}
