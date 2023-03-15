package pl.spjava.gabinet.ejb.facades;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.interceptor.Interceptors;
import jakarta.persistence.*;
import pl.spjava.gabinet.ejb.interceptors.LoggingInterceptor;
import pl.spjava.gabinet.exception.AccountException;
import pl.spjava.gabinet.exception.AppBaseException;
import pl.spjava.gabinet.exception.VisitException;
import pl.spjava.gabinet.model.Account;
import pl.spjava.gabinet.model.Patient;
import pl.spjava.gabinet.model.Visit;

import java.util.ArrayList;
import java.util.List;

@Stateless
@Interceptors(LoggingInterceptor.class)
@RolesAllowed({"Patient", "Admin", "Receptionist"})
public class PatientFacade extends AbstractFacade<Patient>{


    @PersistenceContext(name = "gabinetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }


    public PatientFacade() {
        super(Patient.class);
    }

    public List<Visit> findAllPatientVisitsByLogin(String login) throws AppBaseException {
        try {
            TypedQuery<Visit> typedQuery = em.createNamedQuery("Patient.findAllPatientVisitsByLogin", Visit.class);
            typedQuery.setParameter("login", login);
            return typedQuery.getResultList();
        } catch (NullPointerException nullPointerException){
            throw VisitException.createVisitExceptionWithNoFoundException();
        }
    }

    @Override
    public void edit(Patient entity) throws AppBaseException {
        try {
            super.edit(entity);
            em.flush();
        } catch (OptimisticLockException optimisticLockException) {
            throw AccountException.createAccountExceptionWithOptimisticLockException(entity, optimisticLockException);
        }
    }
}
