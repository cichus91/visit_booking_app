package pl.spjava.gabinet.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import pl.spjava.gabinet.model.Account;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 *
 * @author Marcin Kwapisz
 */
@ApplicationScoped
public class JpaIdentityStore implements IdentityStore {

    private static final Logger LOG = Logger.getLogger(JpaIdentityStore.class.getName());

    @Inject
    private SecurityEndpoint securityEndpoint;

    @Override
    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
        // w tym miejscu moglibyśmy potencjalnie manipulować zestawem grup odczytanym przez metodę validate()
        return IdentityStore.super.getCallerGroups(validationResult);
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        if (credential instanceof UsernamePasswordCredential) {
            UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;
            // Metoda fasady wywołana za pośrednictwem endpointa sprawdza identyczność skrótu hasła oraz stan konta (potwierdzone, aktywne). Niczego nie potrzebujemy tu już robić.
            Account account = securityEndpoint.searchForMatchingAccountAndPassword(usernamePasswordCredential.getCaller(), usernamePasswordCredential.getPasswordAsString());
            return (null != account ? new CredentialValidationResult(account.getLogin(), new HashSet<>(Arrays.asList(account.getType()))) : CredentialValidationResult.INVALID_RESULT);
        }
        return CredentialValidationResult.NOT_VALIDATED_RESULT;

    }

}
