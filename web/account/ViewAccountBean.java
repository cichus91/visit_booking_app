package pl.spjava.gabinet.web.account;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.spjava.gabinet.dto.AccountDTO;
import pl.spjava.gabinet.exception.AppBaseException;

import java.io.Serializable;

@Named
@RequestScoped
public class ViewAccountBean implements Serializable {

    @Inject
    private AccountController accountController;

    private AccountDTO viewAccountDTO;

    @PostConstruct
    private void init(){
        accountController.loadAccountFromStateToView();
        viewAccountDTO = accountController.getViewAccountDTO();
    }

    public AccountDTO getViewAccountDTO() {
        return viewAccountDTO;
    }

    public void setViewAccountDTO(AccountDTO accountDTO) {
        this.viewAccountDTO = accountDTO;
    }

    public String editAccount(AccountDTO accountDTO) throws AppBaseException {
        return accountController.loadAccountToEdit(accountDTO);
    }

    public String activateAccount(AccountDTO accountDTO) throws AppBaseException {
        if (accountDTO.isActive() == false){
            accountController.activateAccount(accountDTO);
            accountController.setViewAccountDTO(null);
            return "index";
        }
        return null;
    }

    public String viewPatientVisits(AccountDTO accountDTO) throws AppBaseException {
        return accountController.viewPatientVisits(accountDTO);
    }
}
