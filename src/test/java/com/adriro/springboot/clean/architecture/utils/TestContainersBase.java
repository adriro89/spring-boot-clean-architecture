package com.adriro.springboot.clean.architecture.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@Transactional
public abstract class TestContainersBase {

    protected static final Network network = Network.newNetwork();
    protected static final GenericContainer<?> h2Container = new GenericContainer<>("buildo/h2database")
            .withExposedPorts(9092)
            .withNetwork(network)
            .withNetworkAliases("h2")
            .withEnv("H2_OPTIONS", "-tcp -tcpAllowOthers -ifNotExists");

    @PersistenceContext
    protected EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        h2Container.start();

        String jdbcUrl = "jdbc:h2:tcp://localhost:" + h2Container.getMappedPort(9092) + "/mem:testdb;DB_CLOSE_DELAY=-1";
        System.setProperty("spring.datasource.url", jdbcUrl);
        System.setProperty("spring.datasource.driver-class-name", "org.h2.Driver");
        System.setProperty("spring.datasource.username", "sa");
        System.setProperty("spring.datasource.password", "password");
    }

    @AfterEach
    public void tearDown() {
        clearDatabase();
        h2Container.stop();
    }

    @Transactional
    private void clearDatabase() {
        if (entityManager != null) {
            entityManager.createNativeQuery("DELETE FROM Post").executeUpdate();
            entityManager.createNativeQuery("ALTER TABLE Post ALTER COLUMN id RESTART WITH 1").executeUpdate();
        }
    }
}
