package com.yushkevich.metric.consumer.repository;

import com.yushkevich.metric.consumer.config.PersistenceProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConnectionTest {

    private C3poDataSource dataSource;
    @Mock
    private PersistenceProperties persistenceProperties;

    @BeforeEach
    void setup() throws SQLException {
        when(persistenceProperties.getDriver()).thenReturn("org.h2.Driver");
        when(persistenceProperties.getJdbcUrl()).thenReturn("jdbc:h2:mem:test");
        when(persistenceProperties.getUser()).thenReturn("user");
        when(persistenceProperties.getPassword()).thenReturn("password");

        dataSource = new C3poDataSource(persistenceProperties);
        dataSource.getConnection().prepareStatement("CREATE TABLE foo ( name text NOT NULL )").execute();
    }

    @Test
    void testInsert() throws SQLException {
        dataSource.getConnection().prepareStatement("INSERT INTO foo(name) VALUES('bar')").execute();

        var resultSet = dataSource.getConnection().prepareStatement("SELECT * FROM foo").executeQuery();

        assertTrue(resultSet.next());
        assertEquals("bar", resultSet.getString("name"));
    }
}
