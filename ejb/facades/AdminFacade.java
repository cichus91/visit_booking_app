package pl.spjava.gabinet.ejb.facades;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.interceptor.Interceptors;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pl.spjava.gabinet.ejb.interceptors.LoggingInterceptor;
import pl.spjava.gabinet.model.Admin;


@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)
@RolesAllowed({"Admin"})
public class AdminFacade extends AbstractFacade<Admin> {

    @PersistenceContext(unitName = "gabinetPU")
    private EntityManager em;

    public AdminFacade() {
        super(Admin.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
