package me.alex.devground.services;


import org.junit.jupiter.api.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.context.properties.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;
import org.testcontainers.containers.*;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class)
@ActiveProfiles("test")
@EnableConfigurationProperties
@Testcontainers
class OffersCRUDTest {

    @Value("${test.docker.mariadb.image}")
    private String dockerImageName;

    @Value("${spring.datasource.password}")
    private String mariadbPassword;
    // container {
    @Container
    public GenericContainer<?> mariadb = new GenericContainer<>(dockerImageName)
            .withExposedPorts(3306).withEnv("MYSQL_ROOT_PASSWORD", mariadbPassword);
    @Autowired
    private OffersCRUD crud;
    // }

    @BeforeEach
    public void setUp() {
        String address = mariadb.getContainerIpAddress();
        Integer port = mariadb.getFirstMappedPort();

        // Now we have an address and port for Redis, no matter where it is running
//        crud = new OffersCRUD(address, port);
    }

    @Test
    public void shouldNotFail() {
        assertTrue(crud.findAll().isEmpty());
    }
}