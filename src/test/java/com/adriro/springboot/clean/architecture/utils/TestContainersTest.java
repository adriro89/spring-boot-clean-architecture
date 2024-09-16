package com.adriro.springboot.clean.architecture.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestContainersTest extends TestContainersBase {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testDatabaseConnection() {
        String query = "SELECT 1";
        Integer result = jdbcTemplate.queryForObject(query, Integer.class);
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void testCustomDatabaseOperations() throws SQLException {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE test_table (id INT PRIMARY KEY, name VARCHAR(255))");
            statement.execute("INSERT INTO test_table (id, name) VALUES (1, 'Test Name')");

            try (var resultSet = statement.executeQuery("SELECT name FROM test_table WHERE id = 1")) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    assertThat(name).isEqualTo("Test Name");
                } else {
                    throw new RuntimeException("No data found");
                }
            }
        }
    }
}
