package pl.spjava.gabinet.ejb.facades;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.*;
import jakarta.interceptor.Interceptors;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.postgresql.util.PSQLException;
import pl.spjava.gabinet.ejb.interceptors.LoggingInterceptor;
import pl.spjava.gabinet.exception.AppBaseException;
import pl.spjava.gabinet.model.Schedule;
import pl.spjava.gabinet.exception.VisitException;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)
@RolesAllowed({"Receptionist"})
public class ScheduleFacade extends AbstractFacade<Schedule>{

    @PersistenceContext(name = "gabinetPU")
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public ScheduleFacade() {
        super(Schedule.class);
    }

    @Override
    public void create(Schedule entity) throws AppBaseException {
        try{
            super.create(entity);
        } catch(PersistenceException persistenceException){
            throw VisitException.createVisitExceptionWithUniqueConstriantException();
        }
    }

}
