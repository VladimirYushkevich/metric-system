package com.yushkevich.metrics.consumer.repository;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.yushkevich.metrics.consumer.config.PersistenceProperties;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class C3poDataSource {

    private final ComboPooledDataSource cpds;

    public C3poDataSource(PersistenceProperties persistenceProperties) {
        this.cpds = new ComboPooledDataSource();

        init(persistenceProperties);
    }

    private void init(PersistenceProperties persistenceProperties) {
        try {
            cpds.setDriverClass(persistenceProperties.getDriver());
            cpds.setJdbcUrl(persistenceProperties.getJdbcUrl());
            cpds.setUser(persistenceProperties.getUser());
            cpds.setPassword(persistenceProperties.getPassword());
        } catch (PropertyVetoException e) {
            throw new IllegalArgumentException("Can't create C3poDataSource", e);
        }
    }

    public Connection getConnection() throws SQLException {
        return cpds.getConnection();
    }
}
