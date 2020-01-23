package me.alex.devground.services;


import me.alex.devground.config.*;
import org.junit.jupiter.api.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;
import org.testcontainers.junit.jupiter.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DemoConfiguration.class})
@ActiveProfiles("test")
@Testcontainers
class OffersCRUDTest {

    @Value("${spring.datasource.username}")
    private String mariadbUsername;

    @Value("${spring.datasource.password}")
    private String mariadbPassword;

    //    @Container
//    public GenericContainer<?> mariadb = new GenericContainer<>("mariadb:10.4.11");
    @Autowired
    private OffersCRUD crud;

    @BeforeEach
    public void setUp() {
//        mariadb.withExposedPorts(3306).withEnv("MYSQL_ROOT_PASSWORD", mariadbPassword);
        System.out.println(mariadbUsername);
        System.out.println(mariadbPassword);
//        String address = mariadb.getContainerIpAddress();
//        Integer port = mariadb.getFirstMappedPort();
    }

    @Test
    public void shouldNotFail() {
        crud.findAll().forEach(x -> System.out.println(x.getDescription()));
    }
}