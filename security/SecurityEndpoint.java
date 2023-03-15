package pl.spjava.gabinet.security;


import jakarta.annotation.security.RunAs;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.Password;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.security.enterprise.identitystore.IdentityStoreHandler;
import pl.spjava.gabinet.dto.AccountDTO;
import pl.spjava.gabinet.ejb.facades.AccountFacade;
import pl.spjava.gabinet.model.Account;

@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@RunAs("AUTHENTICATOR") //w ten sposób ten endpoint udostępnia metodę fasady niedostępną dla "zwykłych" ról
public class SecurityEndpoint {

    @Inject
    private AccountFacade accountFacade;

    @Inject
    private IdentityStoreHandler identityStoreHandler;


    public Account searchForMatchingAccountAndPassword(String login, String shortPassword){
        return accountFacade.searchForMatchingLoginAndPassword(login, shortPassword);
    }

}
