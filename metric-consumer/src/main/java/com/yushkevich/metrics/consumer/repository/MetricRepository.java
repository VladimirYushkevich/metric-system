package com.yushkevich.metrics.consumer.repository;

import org.apache.avro.generic.GenericRecord;
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

    public void insert(GenericRecord genericRecord) throws SQLException {
        var query = "INSERT INTO os_metric(description, name, value, created_at, env) VALUES( ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement;
        try (Connection conn = dataSource.getConnection()) {
            preparedStatement = conn.prepareStatement(query);

            preparedStatement.setString(1, String.valueOf(genericRecord.get("description")));
            preparedStatement.setString(2, String.valueOf(genericRecord.get("name")));
            preparedStatement.setDouble(3, (Double) genericRecord.get("value"));
            preparedStatement.setTimestamp(4, new Timestamp((Long) genericRecord.get("createdAt")));
            preparedStatement.setString(5, String.valueOf(genericRecord.get("env")));

            preparedStatement.execute();

            LOGGER.info("Inserted in DB: {}", genericRecord);
        }
    }
}
