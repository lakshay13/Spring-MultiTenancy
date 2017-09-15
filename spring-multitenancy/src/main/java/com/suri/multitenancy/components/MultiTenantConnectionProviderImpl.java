package com.suri.multitenancy.components;

import com.suri.multitenancy.utils.TenantConstants;
import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider{

    @Autowired
    private DataSource dataSource;

    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void releaseAnyConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public Connection getConnection(String tenantIdentifier) throws SQLException {
        final Connection connection = getAnyConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            // The below statement will change depending on the type of database used. This is valid for Oracle
            statement.execute("ALTER SESSION SET CURRENT_SCHEMA=" + tenantIdentifier);
        }  catch (SQLException e) {
            throw new HibernateException("JDBC connection could not be altered to the schema [" + tenantIdentifier + "]", e);
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        return connection;
    }

    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        try {
            connection.createStatement().execute("ALTER SESSION SET CURRENT_SCHEMA=" + TenantConstants.DEFAULT_TENANT);
        } catch (SQLException e) {
            throw new HibernateException("JDBC connection could not be altered to the schema [" + tenantIdentifier + "]", e);
        } finally {
            connection.close();
        }

    }

    public boolean supportsAggressiveRelease() {
        return true;
    }


    public boolean isUnwrappableAs(Class aClass) {
        return false;
    }

    public <T> T unwrap(Class<T> aClass) {
        return null;
    }
}
