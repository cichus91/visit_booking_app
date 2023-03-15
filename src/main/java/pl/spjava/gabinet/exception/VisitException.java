package pl.spjava.gabinet.exception;

import pl.spjava.gabinet.model.Visit;

public class VisitException extends AppBaseException{

    private static final String KEY_VISIT_TIME_PASS_EX = "error.visit.time.pass.exception";

    private static final String KEY_VISIT_NPE = "NULL POINTER EXCEPTION Przechwycony";

    private static final String KEY_VISIT_ALREADY_RESERVED_EX = "error.visit.already.booked";

    private static final String KEY_VISIT_TIME_CONFLICT_EX = "Visit conficts with a subsequent visit.";

    private static final String KEY_VISIT_UNIQUE_CONSTRIANT_EX = "error.visit.constriant.exception";

    private VisitException(String message, Throwable cause) {
        super(message, cause);
    }

    private VisitException(String message) {
        super(message);
    }

    private Visit visit;

    public Visit getVisit() {
        return visit;
    }

    public static VisitException createVisitExceptionWithThRetryRollBack(){
        VisitException visitException = new VisitException(KEY_TX_RETRY_ROLLBACK);
        return visitException;
    }

    public static VisitException createVisitExceptionWithOptimisticLockException(Visit visit, Throwable cause){
        VisitException visitException = new VisitException(KEY_OPTIMISTIC_LOCK_EX, cause);
        visitException.visit = visit;
        return visitException;
    }

    public static VisitException createVisitExceptionWithNoFoundException(){
        VisitException visitException = new VisitException(KEY_OBJECT_NOT_FOUND_EX);
        return visitException;
    }

    public static VisitException createVisitExceptionWithNoObjectInEJBState(){
        VisitException visitException = new VisitException(KEY_NO_STATE_IN_EJB);
        return visitException;
    }

    public static VisitException createVisitExceptionWithVisitTimePassException(){
        VisitException visitException = new VisitException(KEY_VISIT_TIME_PASS_EX);
        return visitException;
    }

    public static VisitException createVisitExceptionWithNullPointerException(Visit entity, Throwable cause) {
        VisitException visitException = new VisitException(KEY_VISIT_NPE);
        visitException.visit = entity;
        return visitException;
    }

    public static VisitException createVisitExceptionWithUniqueConstriantException() {
        return new VisitException(KEY_VISIT_UNIQUE_CONSTRIANT_EX);
    }

    public static VisitException createVisitExceptionWithIllegalArgumentException(Visit entity,Throwable cause){
        VisitException visitException = new VisitException(KEY_OBJECT_NOT_FOUND_EX);
        visitException.visit = entity;
        return visitException;
    }

    public static VisitException creaVisitExceptionWithVisitAlreadyReserved(Visit visit){
        VisitException visitException = new VisitException(KEY_VISIT_ALREADY_RESERVED_EX);
        visitException.visit = visit;
        return visitException;
    }

    public static VisitException createVisitExceptionWithVisitTimeConflict(Visit visit){
        VisitException visitException = new VisitException(KEY_VISIT_TIME_CONFLICT_EX);
        visitException.visit = visit;
        return visitException;
    }

}
