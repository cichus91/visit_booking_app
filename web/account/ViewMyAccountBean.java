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
public class ViewMyAccountBean implements Serializable {

    @Inject
    private AccountController accountController;

    private AccountDTO viewMyAccountDTO;

    @PostConstruct
    private void init() {
        viewMyAccountDTO = accountController.getMyAccount();
    }

    public AccountDTO getViewMyAccountDTO() {
        return viewMyAccountDTO;
    }

    public void setViewMyAccountDTO(AccountDTO viewMyAccountDTO) {
        this.viewMyAccountDTO = viewMyAccountDTO;
    }

    public String viewMyVisits() throws AppBaseException{
        return accountController.getMyVisitsHistory();
    }

    public String changeMyPassword() throws AppBaseException{
        return accountController.changeMyPassword();
    }

    public String editMyAddress() throws AppBaseException {
        return accountController.editMyAddress(viewMyAccountDTO);
    }
}
