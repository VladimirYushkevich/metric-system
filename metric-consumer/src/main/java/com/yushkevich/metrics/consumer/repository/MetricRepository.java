package com.yushkevich.metrics.consumer.repository;

import com.yushkevich.metrics.commons.message.OSMetric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class MetricRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetricRepository.class);

    private C3poDataSource dataSource;

    public MetricRepository(C3poDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert(OSMetric metric) throws SQLException {
        var query = "INSERT INTO os_metric(description, name, value, created_at, env) VALUES( ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement;
        try (Connection conn = dataSource.getConnection()) {
            preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, metric.getDescription());
            preparedStatement.setString(2, metric.getName());
            preparedStatement.setDouble(3, metric.getValue());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(metric.getCreatedAt()));
            preparedStatement.setString(5, metric.getEnv());

            preparedStatement.execute();

            LOGGER.info("Inserted in DB: {}", metric);
        }
    }
}
