package pl.spjava.gabinet.ejb.managers;

import jakarta.annotation.Resource;
import jakarta.ejb.*;
import jakarta.interceptor.Interceptors;
import pl.spjava.gabinet.ejb.facades.PatientFacade;
import pl.spjava.gabinet.ejb.facades.ReceptionistFacade;
import pl.spjava.gabinet.ejb.facades.AccountFacade;
import pl.spjava.gabinet.ejb.facades.VisitFacade;
import pl.spjava.gabinet.ejb.interceptors.LoggingInterceptor;
import pl.spjava.gabinet.exception.AccountException;
import pl.spjava.gabinet.exception.AppBaseException;
import pl.spjava.gabinet.exception.VisitException;
import pl.spjava.gabinet.model.Patient;
import pl.spjava.gabinet.model.Receptionist;
import pl.spjava.gabinet.model.Visit;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.Date;
import java.util.List;

@Stateful
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LoggingInterceptor.class)
public class VisitManager extends AbstractManager{

    @EJB
    private VisitFacade visitFacade;

    @EJB
    private AccountFacade accountFacade;

    @EJB
    private PatientFacade patientFacade;

    @Resource
    private SessionContext sessionContext;

    private Visit visitState;

    private List<Visit> myVisitsHistory;

    public List<Visit> getMyVisitsHistory() {
        return myVisitsHistory;
    }

    public void setMyVisitsHistory(List<Visit> myVisitsHistory) {
        this.myVisitsHistory = myVisitsHistory;
    }

    public Visit getVisitState() {
        return visitState;
    }

    public void setVisitState(Visit visitState) {
        this.visitState = visitState;
    }

    public void createNewVisit(Visit visit) throws AppBaseException {
        visit.setCreatedBy((Receptionist) accountFacade.findAccountByLogin(sessionContext.getCallerPrincipal().getName()));
        if (isVisitDatePassed(visit)) {
            throw VisitException.createVisitExceptionWithVisitTimePassException();
        } else if (isVisitTimeConfict(visit)){
            throw VisitException.createVisitExceptionWithVisitTimeConflict(visit);
        }
        visitFacade.create(visit);
    }

    public List<Visit> getAllVisits(){
        return visitFacade.findAll();
    }

    public List<Visit> getAllUnbookedVisits() {
        return visitFacade.findAllUnbookedVisits();
    }

    public void deleteVisit(Long id) throws AppBaseException{
        if(visitFacade.find(id).getReserved()){
            throw VisitException.creaVisitExceptionWithVisitAlreadyReserved(visitState);
        }
        visitFacade.remove(visitFacade.find(id));
    }

    public void bookVisit(Long id) throws AppBaseException {
        visitState = visitFacade.find(id);
        if (visitState == null){
            throw VisitException.createVisitExceptionWithNoObjectInEJBState();
        }
        if(isVisitDatePassed(visitState)){
            throw VisitException.createVisitExceptionWithVisitTimePassException();
        }
        if(visitState.getReserved()){
            throw VisitException.creaVisitExceptionWithVisitAlreadyReserved(visitState);
        }
        if(!(accountFacade.findAccountByLogin(sessionContext.getCallerPrincipal().getName())).isActive()){
            throw AccountException.createExceptionAccountInactive((Patient) accountFacade.findAccountByLogin(sessionContext.getCallerPrincipal().getName()));
        }
//        visitState.setReserved(true);
        visitState.setReservedBy((Patient) accountFacade.findAccountByLogin(sessionContext.getCallerPrincipal().getName()));
        visitFacade.edit(visitState);
    }

    public void cancelVisitReservation(Long id) throws AppBaseException {
        visitState = visitFacade.find(id);
//        visitState.setReserved(false);
        visitState.setReservedBy(null);
        visitFacade.edit(visitState);
    }

    public void getMyVisitHistoryIntoState() throws AppBaseException {
        myVisitsHistory = patientFacade.findAllPatientVisitsByLogin(sessionContext.getCallerPrincipal().getName());
    }


    public void loadVisitIntoState(Long id) {
        visitState = visitFacade.find(id);
    }

    // Metody narzędziowe

    private static boolean isVisitDatePassed(Visit visitToCheck){
        return LocalDateTime.now().isAfter(visitToCheck.getVisitDate().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

    public Date getEndOfVisit(Long id){
        Visit visit = visitFacade.find(id);
        Date date = visit.getVisitDate();
        date.toInstant().minusSeconds(visit.getDuration().longValue());
        return date;
    }

    public boolean isVisitTimeConfict(Visit visitToCheck) throws AppBaseException {
        Instant visitToCheckInstant = visitToCheck.getVisitDate().toInstant();
        if(visitFacade.findVisitWithInstant(visitToCheckInstant.minusSeconds(1L)) != null ||
                visitFacade.findVisitWithInstant(visitToCheckInstant.plusSeconds(visitToCheck.getDuration().longValue()*60L).plusSeconds(1L)) != null){
            return true;
        } else {
            return false;
        }
    }
}
