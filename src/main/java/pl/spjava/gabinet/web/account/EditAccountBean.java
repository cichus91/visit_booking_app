package pl.spjava.gabinet.web.account;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.spjava.gabinet.dto.AccountDTO;
import pl.spjava.gabinet.exception.AppBaseException;

import java.io.Serializable;
import java.util.logging.Logger;

@Named
@RequestScoped
public class EditAccountBean implements Serializable {

    private static Logger LOG = Logger.getLogger(EditAccountBean.class.getName());


    @Inject
    private AccountController accountController;

    private AccountDTO editAccountDTO;

    @PostConstruct
    public void init(){
        accountController.loadPatientAccountFromStateToEdit();
        editAccountDTO = accountController.getEditAccountDTO();
    }

    public String savePatient(AccountDTO accountDTO) throws AppBaseException {
        return accountController.savePatient(accountDTO);
    }

    public String saveAccount(AccountDTO accountDTO) throws AppBaseException{
        return accountController.saveAccount(accountDTO);
    }

    public String cancelEdit(){
        accountController.unloadAccountFromState();
        return "index";
    }

    public AccountDTO getEditAccountDTO() {
        return editAccountDTO;
    }

    public void setEditAccountDTO(AccountDTO editAccountDTO) {
        this.editAccountDTO = editAccountDTO;
    }
}
