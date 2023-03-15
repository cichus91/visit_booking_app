package pl.spjava.gabinet.ejb.facades;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.interceptor.Interceptors;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pl.spjava.gabinet.ejb.interceptors.LoggingInterceptor;
import pl.spjava.gabinet.model.Address;

@Stateless
@Interceptors(LoggingInterceptor.class)
@RolesAllowed({"Patient", "Admin", "Receptionist"})
public class AddressFacade extends AbstractFacade<Address> {

    @PersistenceContext(unitName = "gabinetPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AddressFacade() {
        super(Address.class);
    }
}
