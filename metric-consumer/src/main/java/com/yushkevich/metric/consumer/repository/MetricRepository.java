package com.yushkevich.metric.consumer.repository;

import com.yushkevich.metrics.commons.message.OSMetric;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MetricRepository {

    private static final Logger LOGGER = Logger.getLogger(MetricRepository.class.getName());

    private C3poDataSource dataSource;

    public MetricRepository(C3poDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insert(OSMetric metric) {
        var query = "INSERT INTO os_metric(description, name, value, created_at) VALUES( ?, ?, ?, ?)";

        PreparedStatement preparedStatement;
        try (Connection conn = dataSource.getConnection()) {
            preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, metric.getDescription());
            preparedStatement.setString(2, metric.getName());
            preparedStatement.setDouble(3, metric.getValue());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(metric.getCreatedAt()));

            preparedStatement.execute();

            LOGGER.info("Inserted in DB: " + metric);
        } catch (SQLException ex) {
            LOGGER.log(Level.WARNING, "Can't insert in DB: " + metric, ex);
        }
    }
}
