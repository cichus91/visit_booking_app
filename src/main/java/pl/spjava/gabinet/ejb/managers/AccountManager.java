package pl.spjava.gabinet.ejb.managers;

import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.*;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import pl.spjava.gabinet.ejb.facades.AccountFacade;
import pl.spjava.gabinet.ejb.facades.AdminFacade;
import pl.spjava.gabinet.ejb.facades.PatientFacade;
import pl.spjava.gabinet.ejb.facades.ReceptionistFacade;
import pl.spjava.gabinet.ejb.interceptors.LoggingInterceptor;
import pl.spjava.gabinet.exception.AccountException;
import pl.spjava.gabinet.exception.AppBaseException;
import pl.spjava.gabinet.model.*;
import pl.spjava.gabinet.security.HashGenerator;

import java.util.List;

@Stateful
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LoggingInterceptor.class)
public class AccountManager extends AbstractManager{

    @EJB
    private AccountFacade accountFacade;

    @EJB
    private PatientFacade patientFacade;

    @EJB
    private ReceptionistFacade receptionistFacade;

    @EJB
    private AdminFacade adminFacade;

    @Inject
    private HashGenerator hashGenerator;

    @Resource
    private SessionContext sessionContext;

    private Account accountState;

    private String passwordToChange;

    @PermitAll
    public void createPatientAccount(Patient patient) throws AppBaseException {
        patient.setType(Account.AccountType.PATIENT.name());
        hashPassword(patient); //todo do zmiany
        accountFacade.create(patient);
    }

    @RolesAllowed({"Admin"})
    public void createReceptionistAccount(Receptionist receptionist) throws AppBaseException{
        receptionist.setType(Account.AccountType.RECEPTIONIST.name());
        hashPassword(receptionist); //todo do zmiany
        receptionist.setActive(true);
        accountFacade.create(receptionist);
    }

    @RolesAllowed({"Admin"})
    public void createAdminAccount(Admin admin) throws AppBaseException {
        admin.setType(Account.AccountType.ADMIN.name());
        hashPassword(admin); //todo do zmiany
        admin.setActive(true);
        accountFacade.create(admin);
    }

    public Account getAccount(Long id){
        Account account = accountFacade.find(id);
        accountFacade.refresh(account);
        return account;
    }

    public Patient getPatient(Long id){
        Patient patient = (Patient) accountFacade.find(id);
        return patient;
    }

    @RolesAllowed({"Admin", "Patient", "Receptionist"})
    public void savePatient(Patient patient) throws AppBaseException{
        rewritePatientData(patient, (Patient) accountState);
        if (accountState == null){
            throw AccountException.createExceptionWithNoObjectInEJBState();
        }
        patientFacade.edit((Patient) accountState);
        accountState = null;
    }

    @RolesAllowed({"Patient", "Admin", "Receptionist"})
    public void saveAccount(Account account) throws AppBaseException{
        rewritePersonalData(account, accountState);
        if (accountState == null){
            throw AccountException.createExceptionWithNoObjectInEJBState();
        }
        accountFacade.edit(accountState);
        accountState = null;
    }

    @RolesAllowed({"Receptionist"})
    public List<Patient> getAllPatientsList(){
        List<Patient> allPatientsList = patientFacade.findAll();
        return allPatientsList;
    }

    @RolesAllowed({"Admin"})
    public void deleteAccount(Long id) throws AppBaseException{
        accountState = accountFacade.find(id);
        if (accountState == null){
            throw AccountException.createExceptionWithNoObjectInEJBState();
        }
        if (accountState.isActive() == true){
            throw AccountException.createExceptionWithCannotDeleteActiveAccount(accountState);
        }
        accountFacade.remove(accountState);
        accountState = null;
    }

    @RolesAllowed({"Patient", "Admin", "Receptionist"})
    public void loadAccountIntoState(Long id) throws AppBaseException{
        accountState = accountFacade.find(id);
        if (accountState == null){
            throw AccountException.createExceptionWithNoObjectInEJBState();
        }
    }

    @RolesAllowed({"Patient", "Admin", "Receptionist"})
    public Account getAccountState() {
        return accountState;
    }

    @RolesAllowed({"Patient", "Admin", "Receptionist"})
    public void setAccountState(Account accountState) {
        this.accountState = accountState;
    }

    @RolesAllowed({"Admin"})
    public List<Account> getAllAccountsList() {
        return accountFacade.findAll();
    }

    @RolesAllowed({"Patient", "Admin", "Receptionist"})
    public String getPasswordToChange() {
        return passwordToChange;
    }

    public void setPasswordToChange(String passwordToChange) {
        this.passwordToChange = passwordToChange;
    }

    @RolesAllowed({"Admin"})
    public void activateAccount(Long id) throws AppBaseException{
        accountState = accountFacade.find(id);
        accountState.setActive(true);
        accountFacade.edit(accountState);
        accountState = null;
    }

    @RolesAllowed({"Patient", "Receptionist"})
    public List<Visit> getPatientVisits(Long id) throws AppBaseException{
        return patientFacade.find(id).getVisits();
    }

    @RolesAllowed({"Patient", "Admin", "Receptionist"})
    public Account getMyAccount() {
        return accountFacade.findAccountByLogin(sessionContext.getCallerPrincipal().getName());
    }

    @RolesAllowed({"Patient", "Admin", "Receptionist"})
    public String getMyPasswordToChangeIntoState() {
        return passwordToChange = getMyAccount().getPassword();
    }

    @RolesAllowed({"Patient", "Admin", "Receptionist"})
    public void saveMyPasswordChange(String newPassword) throws AppBaseException {
        Account myAccount = getMyAccount();
        myAccount.setPassword(hashGenerator.generateHash(newPassword));
        accountFacade.edit(myAccount);
    }

    @RolesAllowed({"Admin"})
    public void saveEmergencyPasswordChange(String newPassword) throws AppBaseException {
        String hashNewPassword = hashGenerator.generateHash(newPassword);
        accountState.setPassword(hashNewPassword);
        accountFacade.edit(accountState);
        accountState = null;
    }

    //Metody narzędziowe wykorzystywane tylko przez klasę managera

    private static void rewritePersonalData(Account inputAccount, Account accountState){
        accountState.setFirstName(inputAccount.getFirstName());
        accountState.setSecondName(inputAccount.getSecondName());
        accountState.setEmail(inputAccount.getEmail());
        accountState.setPhoneNumber(inputAccount.getPhoneNumber());
    }

    private static void rewritePatientData(Patient inputPatient, Patient patientState){
        rewritePersonalData(inputPatient, patientState);
        patientState.setAddress(inputPatient.getAddress());
        patientState.setDateOfBirth(inputPatient.getDateOfBirth());
    }

    private void hashPassword(Account inputAccount){
        inputAccount.setPassword(hashGenerator.generateHash(inputAccount.getPassword()));
    }
}
