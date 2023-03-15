package pl.spjava.gabinet.config;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.sql.Connection;

@DataSourceDefinition(
        name = "java:app/jdbc/gabinetDS",
        className = "org.postgresql.ds.PGSimpleDataSource",
        user = "postgres",
        password = "Admin1234",
        serverName = "localhost",
        portNumber = 5432,
        databaseName = "dentist",
        isolationLevel = Connection.TRANSACTION_READ_COMMITTED
)
@Stateless
public class JDBConfig {

    @PersistenceContext
    EntityManager entityManager;
}
