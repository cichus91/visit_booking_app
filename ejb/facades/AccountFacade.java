package pl.spjava.gabinet.ejb.facades;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.interceptor.ExcludeClassInterceptors;
import jakarta.interceptor.Interceptors;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import pl.spjava.gabinet.exception.AccountException;
import pl.spjava.gabinet.exception.AppBaseException;
import pl.spjava.gabinet.model.Account;
import pl.spjava.gabinet.ejb.interceptors.LoggingInterceptor;


import java.util.logging.Logger;

@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)
@RolesAllowed({"Patient", "Admin", "Receptionist"})
public class AccountFacade extends AbstractFacade<Account> {

    private static final Logger LOG = Logger.getLogger(AccountFacade.class.getName());

    @PersistenceContext(name = "gabinetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccountFacade() {
        super(Account.class);
    }

    @Override
    @PermitAll
    public void create(Account entity) throws AppBaseException {
        try{
            super.create(entity);
            em.flush();
        } catch (PersistenceException persistenceException){
            throw AccountException.createAccountExceptionWithDbCheckConstraintKey(entity, persistenceException); //todo rozpoznawanie rzeczywistej przyczyny wyjatku
        }

    }

    @Override
    public void edit(Account entity) throws AppBaseException {
        try {
            super.edit(entity);
            em.flush();
        } catch (OptimisticLockException optimisticLockException) {
            throw AccountException.createAccountExceptionWithOptimisticLockException(entity, optimisticLockException);
        }
    }

    @Override
    public void remove(Account entity) throws AppBaseException {
        try{
            super.remove(entity);
            em.flush();
        } catch (OptimisticLockException optimisticLockException){
            throw AccountException.createAccountExceptionWithOptimisticLockException(entity, optimisticLockException);
        }
    }

    @ExcludeClassInterceptors
    @PermitAll
    public Account searchForMatchingLoginAndPassword(String login, String shortPassword){
        if(login == null || shortPassword == null || login.isEmpty() || shortPassword.isEmpty()){
            return null;
        }
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Account> query = criteriaBuilder.createQuery(Account.class);
        Root<Account> from = query.from(Account.class);
        Predicate criteria = criteriaBuilder.conjunction();
        criteria = criteriaBuilder.and(criteria, criteriaBuilder.equal(from.get("login"), login));
        criteria = criteriaBuilder.and(criteria, criteriaBuilder.equal(from.get("password"), shortPassword));
        query = query.select(from);
        query = query.where(criteria);
        TypedQuery<Account> typedQuery = em.createQuery(query);

        return typedQuery.getSingleResult();
    }

    public Account findAccountByLogin(String login) {
        if(login == null){
            return null;
        }
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Account> query = criteriaBuilder.createQuery(Account.class);
        Root<Account> from = query.from(Account.class);
        Predicate criteria = criteriaBuilder.conjunction();
        criteria = criteriaBuilder.and(criteria, criteriaBuilder.equal(from.get("login"), login));
        query = query.select(from);
        query = query.where(criteria);
        TypedQuery<Account> typedQuery = em.createQuery(query);

        return typedQuery.getSingleResult();
    }
}
