package pl.spjava.gabinet.utils;

import pl.spjava.gabinet.dto.AccountDTO;
import pl.spjava.gabinet.dto.AddressDTO;
import pl.spjava.gabinet.model.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class EntityDTOConverter {

    private enum AccountType{
        PATIENT, ADMIN, RECEPTIONIST
    }


    public static Account dtoToEntity(AccountDTO accountDTO) {

        Account account = null;

        switch (accountDTO.getType()){
            case "PATIENT":
                Address address = new Address(accountDTO.getAddressDTO().getStreet(),
                        accountDTO.getAddressDTO().getHouseNumber(),
                        accountDTO.getAddressDTO().getFlatNumber(),
                        accountDTO.getAddressDTO().getZipcode(),
                        accountDTO.getAddressDTO().getPlace());
                Patient patient = new Patient(address);
                patient.setDateOfBirth(accountDTO.getDateOfBirth());
                account = patient;
                break;
            case "ADMIN":
                account = new Admin();
                break;
            case "RECEPTIONIST":
                account = new Receptionist();
                break;
        }

        account.setFirstName(accountDTO.getFirstName());
        account.setSecondName(accountDTO.getSecondName());
        account.setPassword(accountDTO.getPassword());
        account.setActive(accountDTO.isActive());
        account.setEmail(accountDTO.getEmail());
        account.setLogin(accountDTO.getLogin());
        account.setId(accountDTO.getId());
        account.setPhoneNumber(accountDTO.getPhoneNumber());
        return account;

    }

    public static AccountDTO patientEntityToDTO(Patient patient){
        AddressDTO addressDTO = new AddressDTO(patient.getAddress().getStreet(),
                patient.getAddress().getHouseNumber(),
                patient.getAddress().getFlatNumber(),
                patient.getAddress().getZipcode(),
                patient.getAddress().getPlace());
        AccountDTO accountDTO = new AccountDTO(patient.getLogin(),
                patient.getEmail(),
                patient.isActive(),
                patient.getPassword(),
                patient.getFirstName(),
                patient.getSecondName(),
                addressDTO,
                patient.getDateOfBirth(),
                patient.getPhoneNumber());
        accountDTO.setId(patient.getId());
        accountDTO.setType(patient.getType());
        return accountDTO;
    }

    public static AccountDTO accountEntityToDTO(Account account) {
        AccountDTO accountDTO = new AccountDTO(
                account.getId(),
                account.getLogin(),
                account.getEmail(),
                account.isActive(),
                account.getPassword(),
                account.getFirstName(),
                account.getSecondName(),
                account.getPhoneNumber(),
                account.getType());
        return accountDTO;
    }

    public static LocalDate dateToLocalDateConverter(Date date){
        return LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static Date localDateToDateConverter(LocalDate localDate){
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

}
