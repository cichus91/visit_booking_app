package pl.spjava.gabinet.web.account;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.spjava.gabinet.dto.AccountDTO;
import pl.spjava.gabinet.ejb.endpoints.AccountEndpoint;
import pl.spjava.gabinet.exception.AppBaseException;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class PatientsListBean implements Serializable {

    @EJB
    private AccountEndpoint accountEndpoint;

    @Inject
    private AccountController accountController;

    private List<AccountDTO> accountDTOsList;

    @PostConstruct
    private void init(){
        accountDTOsList = accountEndpoint.getAllPatientDTOs(); // toDo - zmienic na wykorzystenie controllera
    }

    public List<AccountDTO> getAccountDTOsList() {
        return accountDTOsList;
    }


    public void deleteAccount(AccountDTO accountDTO) throws AppBaseException {
        accountController.deleteAccount(accountDTO);
        init();
    }

    public String editAccount(AccountDTO accountDTO) throws AppBaseException {
        return accountController.loadAccountToEdit(accountDTO);
    }

    public String viewAccount(AccountDTO accountDTO) throws AppBaseException {
        return accountController.loadAccountToView(accountDTO);
    }
}
