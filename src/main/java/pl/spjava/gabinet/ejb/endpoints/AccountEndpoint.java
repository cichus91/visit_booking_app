package pl.spjava.gabinet.ejb.endpoints;


import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.*;
import jakarta.interceptor.Interceptors;
import pl.spjava.gabinet.dto.AccountDTO;
import pl.spjava.gabinet.dto.VisitDTO;
import pl.spjava.gabinet.ejb.interceptors.LoggingInterceptor;
import pl.spjava.gabinet.ejb.managers.AccountManager;
import pl.spjava.gabinet.exception.AccountException;
import pl.spjava.gabinet.exception.AppBaseException;
import pl.spjava.gabinet.model.*;
import pl.spjava.gabinet.utils.EntityDTOConverter;
import pl.spjava.gabinet.ejb.managers.VisitManager;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@LocalBean
@Interceptors(LoggingInterceptor.class)
@RolesAllowed({"Admin"})
public class AccountEndpoint implements Serializable {



    @EJB
    private AccountManager accountManager;

    @EJB
    private VisitManager visitMaganger;

    @Resource(name = "txRetryLimit")
    private int txRetryLimit;

    @PermitAll
    public void createPatientAccount(AccountDTO accountDTO) throws AppBaseException {
        accountDTO.setType("PATIENT");
        Patient patient = (Patient) EntityDTOConverter.dtoToEntity(accountDTO);

        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do {
            try {
                accountManager.createPatientAccount(patient);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);

        if (rollbackTX && retryTXCounter == 0){
            throw AccountException.createAccountExceptionWithTxRetryRollback();
        }
    }

    @RolesAllowed({"Admin"})
    public void createReceptionistAccount(AccountDTO accountDTO) throws AppBaseException {
        accountDTO.setType("RECEPTIONIST");
        Receptionist receptionist = (Receptionist) EntityDTOConverter.dtoToEntity(accountDTO);

        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do {
            try {
                accountManager.createReceptionistAccount(receptionist);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);

        if (rollbackTX && retryTXCounter == 0){
            throw AccountException.createAccountExceptionWithTxRetryRollback();
        }
    }

    @RolesAllowed({"Admin"})
    public void createAdminAccount(AccountDTO accountDTO) throws AppBaseException {
        accountDTO.setType("ADMIN");
        Admin admin = (Admin) EntityDTOConverter.dtoToEntity(accountDTO);

        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do {
            try {
                accountManager.createAdminAccount(admin);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter > 0);

        if (rollbackTX && retryTXCounter == 0){
            throw AccountException.createAccountExceptionWithTxRetryRollback();
        }
    }

    @RolesAllowed({"Admin"})
    public void deleteAccount(Long id) throws AppBaseException{
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do {
            try {
                accountManager.deleteAccount(id);
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }
        }while (rollbackTX && --retryTXCounter == 0);

        if(rollbackTX && retryTXCounter == 0){
            throw AccountException.createAccountExceptionWithTxRetryRollback();
        }

    }

    @RolesAllowed({"Receptionist"})
    public List<AccountDTO> getAllPatientDTOs() {
        List<AccountDTO> allPatientDTOsList= new ArrayList<>();
        List<Patient> allPatientsList = accountManager.getAllPatientsList();

        for (Patient patient:
             allPatientsList) {
            AccountDTO patientDTO = EntityDTOConverter.patientEntityToDTO(patient);
            allPatientDTOsList.add(patientDTO);
        }

        return allPatientDTOsList;
    }

    @RolesAllowed({"Patient", "Admin", "Receptionist"})
    public void loadAccountIntoManagerState(AccountDTO accountDTO) throws AppBaseException {
        accountManager.loadAccountIntoState(accountDTO.getId());
    }

    @RolesAllowed({"Patient", "Admin", "Receptionist"})
    public AccountDTO loadAccountDTOFromManagerState(){

        if(accountManager.getAccountState() instanceof Patient){
            Patient patient = (Patient) accountManager.getAccountState();
            return EntityDTOConverter.patientEntityToDTO(patient);
        } else {
            return EntityDTOConverter.accountEntityToDTO(accountManager.getAccountState());
        }
    }

    @RolesAllowed({"Admin", "Receptionist", "Patient"})
    public void savePatient(AccountDTO accountDTO) throws AppBaseException{
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do {
            try {
                accountManager.savePatient((Patient) EntityDTOConverter.dtoToEntity(accountDTO));
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }
        }while (rollbackTX && --retryTXCounter == 0);

        if(rollbackTX && retryTXCounter == 0){
            throw AccountException.createAccountExceptionWithTxRetryRollback();
        }

    }

    @RolesAllowed({"Admin", "Receptionist", "Patient"})
    public void saveAccount(AccountDTO accountDTO) throws AppBaseException{
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do {
            try {
                accountManager.saveAccount(EntityDTOConverter.dtoToEntity(accountDTO));
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }
        }while (rollbackTX && --retryTXCounter == 0);

        if(rollbackTX && retryTXCounter == 0){
            throw AccountException.createAccountExceptionWithTxRetryRollback();
        }
    }

    @RolesAllowed({"Admin"})
    public List<AccountDTO> getAllAccountDTOs() {
        List<AccountDTO> allAccountsDTOsList= new ArrayList<>();
        List<Account> allAccountsList = accountManager.getAllAccountsList();
        for (Account account:
                allAccountsList) {
            AccountDTO accountDTO = EntityDTOConverter.accountEntityToDTO(account);
            allAccountsDTOsList.add(accountDTO);
        }

        return allAccountsDTOsList;
    }

    @RolesAllowed({"Admin"})
    public void activateAccount(AccountDTO accountDTO) throws AppBaseException {

        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do {
            try {
                accountManager.activateAccount(accountDTO.getId());
                rollbackTX = accountManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }
        }while (rollbackTX && --retryTXCounter == 0);

        if(rollbackTX && retryTXCounter == 0){
            throw AccountException.createAccountExceptionWithTxRetryRollback();
        }
    }

    @PermitAll
    public void unloadAccountFromManagerState() {
        accountManager.setAccountState(null);
    }

    @RolesAllowed({"Patient", "Receptionist"})
    public List<VisitDTO> getPatientVisits(AccountDTO accountDTO) throws AppBaseException {
        List<Visit> visits = accountManager.getPatientVisits(accountDTO.getId());
        List<VisitDTO> visitDTOS = new ArrayList<>();
        for (Visit visit:
             visits) {
            VisitDTO visitDTO = new VisitDTO(visit.getDuration(), visit.getVisitDate(), visit.getId(), visit.getReserved());
            visitDTOS.add(visitDTO);
        }
        return visitDTOS;
    }

    @RolesAllowed({"Admin", "Patient", "Receptionist"})
    public AccountDTO geMyAccount(){
        if(accountManager.getMyAccount() instanceof Patient){
            return EntityDTOConverter.patientEntityToDTO((Patient) accountManager.getMyAccount());
        } else {
            return EntityDTOConverter.accountEntityToDTO(accountManager.getMyAccount());
        }
    }

    @RolesAllowed({"Patient"})
    public void getMyVisitsHistory() throws AppBaseException{
        visitMaganger.getMyVisitHistoryIntoState();
    }

    @RolesAllowed({"Patient"})
    public List<VisitDTO> loadMyVisitsHistoryFromState(){
        try {
            List<Visit> allMyVisitsHistory = visitMaganger.getMyVisitsHistory();
            List<VisitDTO> allMyVisitsHistoryDTO = new ArrayList<>();
            for (Visit visit :
                    allMyVisitsHistory) {
                VisitDTO visitDTO = new VisitDTO(visit.getDuration(), visit.getVisitDate(), visit.getId(), visit.getReserved());
                visitDTO.setCreatedBy(EntityDTOConverter.accountEntityToDTO(visit.getCreatedBy()));
                if (visit.getReserved() == true) {
                    visitDTO.setReservedBy(EntityDTOConverter.patientEntityToDTO(visit.getReservedBy()));
                }
                allMyVisitsHistoryDTO.add(visitDTO);
            }
            return allMyVisitsHistoryDTO;
        } catch (NullPointerException nullPointerException){
            List<VisitDTO> emptyVisitList = new ArrayList<>();
            return emptyVisitList;
        }
    }

    @RolesAllowed({"Admin", "Receptionist", "Patient"})
    public void changeMyPassword() throws AppBaseException{
        accountManager.getMyPasswordToChangeIntoState();
    }

    @RolesAllowed({"Admin", "Receptionist", "Patient"})
    public String loadMyPasswordFromState(){
        return accountManager.getPasswordToChange();
    }

    @RolesAllowed({"Admin", "Receptionist", "Patient"})
    public void saveMyPasswordChange(String newPassword) throws AppBaseException {
        accountManager.saveMyPasswordChange(newPassword);
    }

    public void confirmEmergencyPasswordChange(String newPassword) throws AppBaseException {
        accountManager.saveEmergencyPasswordChange(newPassword);
    }
}
