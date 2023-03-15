package pl.spjava.gabinet.ejb.facades;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.interceptor.Interceptors;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import pl.spjava.gabinet.ejb.interceptors.LoggingInterceptor;
import pl.spjava.gabinet.exception.AccountException;
import pl.spjava.gabinet.exception.AppBaseException;
import pl.spjava.gabinet.exception.VisitException;
import pl.spjava.gabinet.model.Visit;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)
@RolesAllowed({"Patient", "Receptionist"})
public class VisitFacade extends AbstractFacade<Visit> {

    @PersistenceContext(name = "gabinetPU")
    private EntityManager em;

    public VisitFacade() {
        super(Visit.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void remove(Visit entity) throws AppBaseException {
        try {
            super.remove(entity);
            em.flush();
        } catch (OptimisticLockException optimisticLockException){
            VisitException.createVisitExceptionWithOptimisticLockException(entity, optimisticLockException);
        } catch (NullPointerException nullPointerException){
            VisitException.createVisitExceptionWithNullPointerException(entity, nullPointerException);
        } catch (IllegalArgumentException illegalArgumentException) {
            VisitException.createVisitExceptionWithIllegalArgumentException(entity, illegalArgumentException);
        }
    }

    @Override
    public void edit(Visit entity) throws AppBaseException {
        try {
            super.edit(entity);
        } catch (OptimisticLockException optimisticLockException) {
            throw VisitException.createVisitExceptionWithOptimisticLockException(entity, optimisticLockException);
        } catch (NullPointerException nullPointerException){
            VisitException.createVisitExceptionWithNullPointerException(entity, nullPointerException);
        } catch (IllegalArgumentException illegalArgumentException) {
            VisitException.createVisitExceptionWithIllegalArgumentException(entity, illegalArgumentException);
        }
    }

    public Visit findVisitWithInstant(Instant instant) throws AppBaseException{
        try {
            TypedQuery<Visit> typedQuery = (TypedQuery<Visit>) em.createNamedQuery("findVisitByDate");
            typedQuery.setParameter("date", Date.from(instant));
            return typedQuery.getSingleResult();
        }catch (NullPointerException nullPointerException){
            return null;
        } catch (NoResultException noResultException){
            return null;
        }
    }

    public List<Visit> findAllUnbookedVisits(){
        TypedQuery<Visit> typedQuery = (TypedQuery<Visit>) em.createNamedQuery("findAllUnbookedVisits");
        return typedQuery.getResultList();
    }


//    public List<Visit> findAllMyVisits(String login) {
//        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
//        CriteriaQuery<Visit> query = criteriaBuilder.createQuery(Visit.class);
//        Root<Visit> from = query.from(Visit.class);
//        Predicate criteria = criteriaBuilder.conjunction();
//        criteria = criteriaBuilder.and(criteria, criteriaBuilder.equal(from.get("reservedby_id"), 1));
//        query = query.select(from);
//        query = query.where(criteria);
//        TypedQuery<Visit> typedQuery = em.createQuery(query);
//        return typedQuery.getResultList();
//    }
}
