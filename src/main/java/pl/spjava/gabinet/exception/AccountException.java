package pl.spjava.gabinet.exception;

import pl.spjava.gabinet.model.Account;

public class AccountException extends AppBaseException{

    static final public String KEY_DB_CONSTRAINT = "error.account.db.constraint.uniq.login"; //todo poprawic nazwe klucza, na bardziej szczegolowa

    static final public String KEY_CANNOT_DELETE_ACTIVE_ACCOUNT = "error.account.cannot.delete";

    static final public String KEY_INACTIVE_ACCOUNT = "error.account.is.inactive";

    private AccountException(String message, Throwable cause) {
        super(message, cause);
    }

    private AccountException(String message) {
        super(message);
    }

    private Account account;

    public Account getAccount() {
        return account;
    }

    public static AccountException createAccountExceptionWithTxRetryRollback(){
        AccountException accountException = new AccountException(KEY_TX_RETRY_ROLLBACK);
        return accountException;
    }

    public static AccountException createAccountExceptionWithDbCheckConstraintKey(Account account, Throwable causeThrowable) {
        AccountException accountException = new AccountException(KEY_DB_CONSTRAINT, causeThrowable);
        accountException.account = account;
        return accountException;
    }

    public static AccountException createAccountExceptionWithOptimisticLockException(Account account, Throwable causeThrowable){
        AccountException accountException = new AccountException(KEY_OPTIMISTIC_LOCK_EX, causeThrowable);
        accountException.account = account;
        return accountException;
    }

    public static AccountException createAccountExceptionWithNotFoundException(){
        AccountException accountException = new AccountException(KEY_OBJECT_NOT_FOUND_EX);
        return accountException;
    }

    public static AccountException createExceptionWithNoObjectInEJBState(){
        AccountException accountException = new AccountException(KEY_NO_STATE_IN_EJB);
        return accountException;
    }

    public static AccountException createExceptionWithCannotDeleteActiveAccount(Account account) {
        AccountException accountException = new AccountException(KEY_CANNOT_DELETE_ACTIVE_ACCOUNT);
        accountException.account = account;
        return accountException;
    }

    public static AccountException createExceptionAccountInactive(Account account){
        AccountException accountException = new AccountException(KEY_INACTIVE_ACCOUNT);
        accountException.account = account;
        return accountException;
    }
}
