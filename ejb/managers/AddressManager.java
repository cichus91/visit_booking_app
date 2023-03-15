package pl.spjava.gabinet.ejb.managers;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import pl.spjava.gabinet.ejb.facades.AddressFacade;
import pl.spjava.gabinet.ejb.interceptors.LoggingInterceptor;
import pl.spjava.gabinet.exception.AppBaseException;
import pl.spjava.gabinet.model.Address;

@Stateless
@Interceptors(LoggingInterceptor.class)
public class AddressManager {

    @EJB
    private AddressFacade addressFacade;

    public void createAddress(Address address) throws AppBaseException {
        addressFacade.create(address);
    }

    public void removeAddress(Long id) throws AppBaseException{
        addressFacade.remove(addressFacade.find(id));
    }

    public Address getAddressEntity(Long id){
        Address gottenAddress = addressFacade.find(id);
        addressFacade.refresh(gottenAddress);
        return gottenAddress;
    }
}
