package pl.spjava.gabinet.web.account;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import pl.spjava.gabinet.dto.AccountDTO;
import pl.spjava.gabinet.dto.AddressDTO;
import pl.spjava.gabinet.dto.VisitDTO;
import pl.spjava.gabinet.ejb.endpoints.AccountEndpoint;
import pl.spjava.gabinet.exception.AccountException;
import pl.spjava.gabinet.exception.AppBaseException;
import pl.spjava.gabinet.utils.ContextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@SessionScoped
public class AccountController implements Serializable {
//todo wprowadzic przechwytywanie wyjatkow aplikacyjnych "Action Listener"
    private static final Logger LOG = Logger.getLogger(AccountController.class.getName());

    @EJB
    private AccountEndpoint accountEndpoint;

    private AccountDTO createPatientDTO;

    private AccountDTO createReceptionistDTO;

    private AccountDTO createAdminDTO;

    private AccountDTO confirmAccountDTO;

    private AccountDTO editAccountDTO;

    private AccountDTO viewAccountDTO;

    private List<VisitDTO> viewPatientVisits = new ArrayList<>();

    public AccountDTO getCreatePatientDTO() {
        return createPatientDTO;
    }

    public AccountDTO getViewAccountDTO() {
        return viewAccountDTO;
    }

    public void setViewAccountDTO(AccountDTO viewAccountDTO) {
        this.viewAccountDTO = viewAccountDTO;
    }

    public AccountDTO getEditAccountDTO() {
        return editAccountDTO;
    }

    public void setEditAccountDTO(AccountDTO editAccountDTO) {
        this.editAccountDTO = editAccountDTO;
    }

    public AccountDTO getConfirmAccountDTO() {
        return confirmAccountDTO;
    }

    public List<VisitDTO> getViewPatientVisits() {
        return viewPatientVisits;
    }

    public void setViewPatientVisits(List<VisitDTO> viewPatientVisits) {
        this.viewPatientVisits = viewPatientVisits;
    }

    public String createNewPatient(AccountDTO accountDTO, AddressDTO addressDTO){
        try{
            createPatientDTO = accountDTO;
            createPatientDTO.setAddressDTO(addressDTO);
            accountEndpoint.createPatientAccount(createPatientDTO);
            createPatientDTO = null;
            LOG.info("Account " +accountDTO.getLogin()+ " successfully confirmed.");
            return "index";
        } catch (AccountException accountException){
            if (AccountException.KEY_DB_CONSTRAINT.equals(accountException.getMessage())){
                ContextUtils.emitInternationalizedMessage("login", AccountException.KEY_DB_CONSTRAINT);
            }else {
                LOG.log(Level.SEVERE, "Zgłoszono wyjątek aplikacyjny: ", accountException);
            }
            return null;
        } catch (AppBaseException appBaseException){
            LOG.log(Level.SEVERE, "Zgłoszono wyjątek aplikacyjny: ", appBaseException);
            if (ContextUtils.isInternationalizationKeyExist((appBaseException.getMessage()))){
                ContextUtils.emitInternationalizedMessage(null, appBaseException.getMessage());
            }
            return null;
        }
    }

    public String createNewReceptionist(AccountDTO accountDTO){
        try{
            createReceptionistDTO = accountDTO;
            accountEndpoint.createReceptionistAccount(createReceptionistDTO);
            LOG.info("Account " +accountDTO.getLogin()+ " successfully confirmed.");
            createReceptionistDTO = null;
            return "index";
        } catch (AccountException accountException){
            if (AccountException.KEY_DB_CONSTRAINT.equals(accountException.getMessage())){
                ContextUtils.emitInternationalizedMessage("login", AccountException.KEY_DB_CONSTRAINT);
            }else {
                LOG.log(Level.SEVERE, "Zgłoszono wyjątek aplikacyjny: ", accountException);
            }
            return null;
        } catch (AppBaseException appBaseException){
            LOG.log(Level.SEVERE, "Zgłoszono wyjątek aplikacyjny: ", appBaseException);
            if (ContextUtils.isInternationalizationKeyExist((appBaseException.getMessage()))){
                ContextUtils.emitInternationalizedMessage(null, appBaseException.getMessage());
            }
            return null;
        }
    }

    public String confirmAccountCreation(AccountDTO accountDTO, AddressDTO addressDTO){
            confirmAccountDTO = accountDTO;
            confirmAccountDTO.setAddressDTO(addressDTO);
            LOG.info("Created new account " + accountDTO.getLogin() + " need to be confirmed");
            return "confirmaccount";
    }

    public String loadAccountToEdit(AccountDTO accountDTO) throws AppBaseException {
        try{
            accountEndpoint.loadAccountIntoManagerState(accountDTO);
            return "editaccount";
        } catch (AppBaseException appBaseException){
            Logger lg = Logger.getLogger(AccountController.class.getName());
            lg.log(Level.SEVERE, "Zgłoszenie w metodzie akcji wyjatku: ", appBaseException);
            ContextUtils.emitInternationalizedMessageOfException(appBaseException);
            return null;
        }
    }

    public String loadAccountToEditFromAdminView(AccountDTO accountDTO) throws AppBaseException {
        try{
            accountEndpoint.loadAccountIntoManagerState(accountDTO);
            return "editaccountadminview";
        } catch (AppBaseException appBaseException){
            Logger lg = Logger.getLogger(AccountController.class.getName());
            lg.log(Level.SEVERE, "Zgłoszenie w metodzie akcji wyjatku: ", appBaseException);
            ContextUtils.emitInternationalizedMessageOfException(appBaseException);
            return null;
        }
    }

    public String loadAccountToView(AccountDTO accountDTO) throws AppBaseException {
        try{
            accountEndpoint.loadAccountIntoManagerState(accountDTO);
            return "viewaccount";
        } catch (AppBaseException appBaseException){
            Logger lg = Logger.getLogger(AccountController.class.getName());
            lg.log(Level.SEVERE, "Zgłoszenie w metodzie akcji wyjatku: ", appBaseException);
            ContextUtils.emitInternationalizedMessageOfException(appBaseException);
            return null;
        }
    }

    public void loadPatientAccountFromStateToEdit(){
        editAccountDTO = accountEndpoint.loadAccountDTOFromManagerState();
    }

    public void loadAccountFromStateToView(){
        viewAccountDTO = accountEndpoint.loadAccountDTOFromManagerState();
    }

    public String savePatient(AccountDTO accountDTO) throws AppBaseException{
        try {
            accountEndpoint.savePatient(accountDTO);
            return "index";
        } catch (AppBaseException appBaseException){
            if (ContextUtils.isInternationalizationKeyExist((appBaseException.getMessage()))){
                ContextUtils.emitInternationalizedMessage(null, appBaseException.getMessage());
            }
            return null;
        }
    }

    public String saveAccount(AccountDTO accountDTO) throws AppBaseException{
        try {
            accountEndpoint.saveAccount(accountDTO);
            return "index";
        } catch (AppBaseException appBaseException){
            if (ContextUtils.isInternationalizationKeyExist((appBaseException.getMessage()))){
                ContextUtils.emitInternationalizedMessage(null, appBaseException.getMessage());
            }
            return null;
        }
    }

    public void deleteAccount(AccountDTO accountDTO) throws AppBaseException{
        try{
            accountEndpoint.deleteAccount(accountDTO.getId());
        } catch (AccountException accountException){
            if (AccountException.KEY_CANNOT_DELETE_ACTIVE_ACCOUNT.equals(accountException.getMessage())){
                ContextUtils.emitInternationalizedMessage("login", AccountException.KEY_CANNOT_DELETE_ACTIVE_ACCOUNT);
            }else {
                LOG.log(Level.SEVERE, "Zgłoszono wyjątek aplikacyjny: ", accountException);
            }
        }
    }

    public String createNewAdmin(AccountDTO accountDTO) throws AppBaseException{
        try {
            createAdminDTO = accountDTO;
            accountEndpoint.createAdminAccount(createAdminDTO);
            LOG.info("Account " + accountDTO.getLogin() + " successfully confirmed.");
            createReceptionistDTO = null;
            return "index";
        } catch (AccountException accountException){
            if (AccountException.KEY_DB_CONSTRAINT.equals(accountException.getMessage())){
                ContextUtils.emitInternationalizedMessage("login", AccountException.KEY_DB_CONSTRAINT);
            }else {
                LOG.log(Level.SEVERE, "Zgłoszono wyjątek aplikacyjny: ", accountException);
            }
            return null;
        } catch (AppBaseException appBaseException){
            LOG.log(Level.SEVERE, "Zgłoszono wyjątek aplikacyjny: ", appBaseException);
            if (ContextUtils.isInternationalizationKeyExist((appBaseException.getMessage()))){
                ContextUtils.emitInternationalizedMessage(null, appBaseException.getMessage());
            }
            return null;
        }
    }

    public void activateAccount(AccountDTO accountDTO) throws AppBaseException{
        try {
            accountEndpoint.activateAccount(accountDTO);
        } catch (AppBaseException appBaseException) {
            Logger lg = Logger.getLogger(AccountController.class.getName());
            lg.log(Level.SEVERE, "Zgłoszenie w metodzie akcji wyjatku: ", appBaseException);
            ContextUtils.emitInternationalizedMessageOfException(appBaseException);
        }
    }

    public void unloadAccountFromState() {
        accountEndpoint.unloadAccountFromManagerState();
        editAccountDTO = null;
    }

    public String viewPatientVisits(AccountDTO accountDTO) throws AppBaseException {
        try{
            viewPatientVisits = accountEndpoint.getPatientVisits(accountDTO);
            return "viewpatientvisits";
        } catch (AppBaseException appBaseException){
            Logger lg = Logger.getLogger(AccountController.class.getName());
            lg.log(Level.SEVERE, "Zgłoszenie w metodzie akcji wyjatku: ", appBaseException);
            ContextUtils.emitInternationalizedMessageOfException(appBaseException);
            return null;
        }
    }

    public AccountDTO getMyAccount(){
        return accountEndpoint.geMyAccount();
    }

    public String getMyVisitsHistory() throws AppBaseException{
        try{
            accountEndpoint.getMyVisitsHistory();
            return "viewmyvisitshistory";
        } catch (AppBaseException appBaseException) {
            Logger lg = Logger.getLogger(AccountController.class.getName());
            lg.log(Level.SEVERE, "Zgłoszenie w metodzie akcji wyjatku: ", appBaseException);
            ContextUtils.emitInternationalizedMessageOfException(appBaseException);
            return null;
        }
    }

    public List<VisitDTO> loadMyVisitHistoryFromState(){
        return accountEndpoint.loadMyVisitsHistoryFromState();
    }

    public String changeMyPassword() throws AppBaseException{
        try{
            accountEndpoint.changeMyPassword();
            return "changemypassword";
        } catch (AppBaseException appBaseException){
            Logger lg = Logger.getLogger(AccountController.class.getName());
            lg.log(Level.SEVERE, "Zgłoszenie w metodzie akcji wyjatku: ", appBaseException);
            ContextUtils.emitInternationalizedMessageOfException(appBaseException);
            return null;
        }
    }

    public String loadMyPasswordToChange(){
        return accountEndpoint.loadMyPasswordFromState();
    }

    public String confirmMyPasswordChange(String newPassword) throws AppBaseException {
        try {
            accountEndpoint.saveMyPasswordChange(newPassword);
            return "success";

        } catch (AppBaseException appBaseException) {
            Logger lg = Logger.getLogger(AccountController.class.getName());
            lg.log(Level.SEVERE, "Zgłoszenie w metodzie akcji wyjatku: ", appBaseException);
            ContextUtils.emitInternationalizedMessageOfException(appBaseException);
            return null;
        }
    }

    public String confirmEmergencyPasswordChange(String newPassword) throws AppBaseException {
        try{
            accountEndpoint.confirmEmergencyPasswordChange(newPassword);
            return "success";
        //tu powinien byc wyslany mail do pacjenta z nowym haslem
        } catch (AppBaseException appBaseException) {
            Logger lg = Logger.getLogger(AccountController.class.getName());
            lg.log(Level.SEVERE, "Zgłoszenie w metodzie akcji wyjatku: ", appBaseException);
            ContextUtils.emitInternationalizedMessageOfException(appBaseException);
            return null;
        }
    }

    public String loadAccountToEmergencyPasswordChange(AccountDTO accountDTO) throws AppBaseException {
        try{
            accountEndpoint.loadAccountIntoManagerState(accountDTO);
            return "emergencypasswordchange";
        } catch (AppBaseException appBaseException) {
            Logger lg = Logger.getLogger(AccountController.class.getName());
            lg.log(Level.SEVERE, "Zgłoszenie w metodzie akcji wyjatku: ", appBaseException);
            ContextUtils.emitInternationalizedMessageOfException(appBaseException);
            return null;
        }
    }

    public String editMyAddress(AccountDTO accountDTO) throws AppBaseException {
        try {
            accountEndpoint.loadAccountIntoManagerState(accountDTO);
            return "editmyaddress";
        } catch (AppBaseException appBaseException) {
            Logger lg = Logger.getLogger(AccountController.class.getName());
            lg.log(Level.SEVERE, "Zgłoszenie w metodzie akcji wyjatku: ", appBaseException);
            ContextUtils.emitInternationalizedMessageOfException(appBaseException);
            return null;
        }
    }

}
