package pl.spjava.gabinet.ejb.endpoints;

import jakarta.annotation.Resource;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.*;

import jakarta.interceptor.Interceptors;
import pl.spjava.gabinet.dto.ScheduleDTO;
import pl.spjava.gabinet.dto.VisitDTO;
import pl.spjava.gabinet.ejb.interceptors.LoggingInterceptor;
import pl.spjava.gabinet.ejb.managers.VisitManager;
import pl.spjava.gabinet.exception.AppBaseException;
import pl.spjava.gabinet.exception.VisitException;
import pl.spjava.gabinet.model.Visit;
import pl.spjava.gabinet.utils.EntityDTOConverter;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateful
@TransactionAttribute(TransactionAttributeType.NEVER)
@LocalBean
@Interceptors(LoggingInterceptor.class)
@RolesAllowed({"Receptionist"})
public class VisitEndpoint implements Serializable {

    @EJB
    private VisitManager visitManager;

    @Resource(name = "txRetryLimit")
    private int txRetryLimit;


    public void createNewVisit(VisitDTO visitDTO) throws AppBaseException {
        Visit visit = new Visit();
        visit.setVisitDate(visitDTO.getVisitDate());
        visit.setDuration(visitDTO.getDuration());

        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do{
            try{
                visitManager.createNewVisit(visit);
                rollbackTX = visitManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter == 0);

        if (rollbackTX && retryTXCounter == 0){
            throw VisitException.createVisitExceptionWithThRetryRollBack();
        }
    }

    public List<VisitDTO> getAllVisits(){
        List<VisitDTO> visitDTOList = new ArrayList<>();
        List<Visit> visitList = visitManager.getAllVisits();
        for (Visit visit:
             visitList) {
            VisitDTO visitDTO = new VisitDTO(visit.getDuration(), visit.getVisitDate(), visit.getId(), visit.getReserved());
            visitDTO.setCreatedBy(EntityDTOConverter.accountEntityToDTO(visit.getCreatedBy()));
            if (visit.getReserved() == true) {
                visitDTO.setReservedBy(EntityDTOConverter.patientEntityToDTO(visit.getReservedBy()));
            }
            visitDTOList.add(visitDTO);
        }
        return visitDTOList;
    }

    @RolesAllowed({"Receptionist", "Patient"})
    public List<VisitDTO> getAllUnbookedVisits(){
        List<VisitDTO> visitDTOList = new ArrayList<>();
        List<Visit> visitList = visitManager.getAllUnbookedVisits();
        for (Visit visit:
                visitList) {
            VisitDTO visitDTO = new VisitDTO(visit.getDuration(), visit.getVisitDate(), visit.getId(), visit.getReserved());
            visitDTO.setCreatedBy(EntityDTOConverter.accountEntityToDTO(visit.getCreatedBy()));
            if (visit.getReserved() == true) {
                visitDTO.setReservedBy(EntityDTOConverter.patientEntityToDTO(visit.getReservedBy()));
            }
            visitDTOList.add(visitDTO);
        }
        return visitDTOList;
    }

    public void deleteVisit(VisitDTO visitDTO) throws AppBaseException{
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do{
            try{
                visitManager.deleteVisit(visitDTO.getId());
                rollbackTX = visitManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter == 0);

        if (rollbackTX && retryTXCounter == 0){
            throw VisitException.createVisitExceptionWithThRetryRollBack();
        }
    }

    @RolesAllowed({"Patient"})
    public void bookVisit(VisitDTO visitDTO) throws AppBaseException{
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do{
            try{
                visitManager.bookVisit(visitDTO.getId());
                rollbackTX = visitManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter == 0);

        if (rollbackTX && retryTXCounter == 0){
            throw VisitException.createVisitExceptionWithThRetryRollBack();
        }
    }

    @RolesAllowed({"Receptionist", "Patient"})
    public void cancelVisitReservation(VisitDTO visitDTO) throws AppBaseException{
        boolean rollbackTX;
        int retryTXCounter = txRetryLimit;

        do{
            try{
                visitManager.cancelVisitReservation(visitDTO.getId());
                rollbackTX = visitManager.isLastTransactionRollback();
            } catch (EJBTransactionRolledbackException ex) {
                Logger.getGlobal().log(Level.SEVERE, "Próba " + retryTXCounter
                        + " wykonania metody biznesowej zakończona wyjątkiem klasy:"
                        + ex.getClass().getName());
                rollbackTX = true;
            }
        } while (rollbackTX && --retryTXCounter == 0);

        if (rollbackTX && retryTXCounter == 0){
            throw VisitException.createVisitExceptionWithThRetryRollBack();
        }
    }

    public void loadVisitIntoManagerState(VisitDTO visitDTO) {
        visitManager.loadVisitIntoState(visitDTO.getId());
    }

    public VisitDTO loadVisitFromManagerState(){
        Visit visit = visitManager.getVisitState();
        VisitDTO visitDTO = new VisitDTO(visit.getDuration(), visit.getVisitDate(), visit.getId(), visit.getReserved());
        visitDTO.setCreatedBy(EntityDTOConverter.accountEntityToDTO(visit.getCreatedBy()));
        if (visit.getReserved() == true) {
            visitDTO.setReservedBy(EntityDTOConverter.patientEntityToDTO(visit.getReservedBy()));
        }
        return visitDTO;
    }

}
