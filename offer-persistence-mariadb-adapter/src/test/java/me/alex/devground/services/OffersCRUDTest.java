package me.alex.devground.services;


import me.alex.devground.config.*;
import org.junit.jupiter.api.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.util.*;
import org.springframework.context.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;
import org.testcontainers.containers.*;
import org.testcontainers.containers.wait.strategy.*;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DemoConfiguration.class})
@ContextConfiguration(initializers = OffersCRUDTest.Initializer.class)
@ActiveProfiles("test")
@Testcontainers
class OffersCRUDTest {

    @Container
    public static GenericContainer<?> mariadb = new GenericContainer<>("mariadb:10.4.11")
            .withExposedPorts(3306).withEnv("MYSQL_ROOT_PASSWORD", "secret")
            .waitingFor(Wait.forLogMessage(".*Temporary server started.*", 1));
    @Value("${spring.datasource.url}")
    private String mariadbUrl;
    @Value("${spring.datasource.username}")
    private String mariadbUsername;
    @Value("${spring.datasource.password}")
    private String mariadbPassword;
    //            .withCreateContainerCmdModifier(it -> {
//                it.withHealthcheck(new HealthCheck().withTest(Collections.singletonList("mysqladmin -p" + "secret" + " ping")));
//            })
//            .waitingFor(Wait.forHealthcheck());
    @Autowired
    private OffersCRUD crud;

    @BeforeEach
    public void setUp() {
        System.out.println(mariadbUrl);
        System.out.println(mariadbUsername);
        System.out.println(mariadbPassword);
//        String address = mariadb.getContainerIpAddress();
        System.out.println(mariadb.getContainerIpAddress());
        Integer port = mariadb.getFirstMappedPort();
        System.out.println(port);
    }

    @Test
    public void shouldNotFail() {
        crud.findAll().forEach(x -> System.out.println(x.getDescription()));
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url="
                            + "jdbc:mariadb://" + mariadb.getContainerIpAddress() + ":" + mariadb.getFirstMappedPort() + "/mydb",
                    "spring.datasource.username=" + "root",
                    "spring.datasource.password=" + "secret"
            ).applyTo(configurableApplicationContext.getEnvironment());
        }

    }
}