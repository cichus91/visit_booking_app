package pl.spjava.gabinet.ejb.endpoints;


import jakarta.ejb.EJB;
import jakarta.ejb.Stateful;
import jakarta.interceptor.Interceptors;
import pl.spjava.gabinet.dto.AddressDTO;
import pl.spjava.gabinet.ejb.interceptors.LoggingInterceptor;
import pl.spjava.gabinet.ejb.managers.AddressManager;
import pl.spjava.gabinet.exception.AppBaseException;
import pl.spjava.gabinet.model.Address;

import java.io.Serializable;

@Stateful
@Interceptors(LoggingInterceptor.class)
public class AddressEndpoint implements Serializable {

    @EJB
    private AddressManager addressManager;

    private Address address;

    public void createAddress(AddressDTO addressDTO) throws AppBaseException {
        addressManager.createAddress(addressDTOToAddressConverter(addressDTO));
    }

    public static Address addressDTOToAddressConverter(AddressDTO addressDTO){
        return new Address(addressDTO.getStreet(),
                addressDTO.getHouseNumber(),
                addressDTO.getFlatNumber(),
                addressDTO.getZipcode(),
                addressDTO.getPlace());
    }

}
