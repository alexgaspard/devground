package me.alex.devground.services;


import me.alex.devground.config.*;
import me.alex.devground.models.*;
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

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DemoConfiguration.class})
//@SpringBootTest(classes = {ConfigFileApplicationContextInitializer.class, DemoConfiguration.class})
@ContextConfiguration(initializers = OffersCRUDTest.Initializer.class)
@ActiveProfiles("test")
@Testcontainers
class OffersCRUDTest {

    @Value("${spring.datasource.url}")
    private String mariadbUrl;
    @Value("${spring.datasource.username}")
    private String mariadbUsername;
    @Value("${spring.datasource.password}")
    private String mariadbPassword;

    private static final String SECRET = "secret";
    private static final String DB = "mydb";

    @Container
    public static GenericContainer<?> mariadb = new GenericContainer<>("mypostgre:12")
            .withExposedPorts(5432)
            .withEnv("POSTGRES_PASSWORD", SECRET)
            .withEnv("POSTGRES_DB", DB)
//            .waitingFor(Wait.forLogMessage(".*Temporary server started.*", 1));
//            .withCreateContainerCmdModifier(it -> {
//                it.withHealthcheck(new HealthCheck().withTest(Collections.singletonList("mysqladmin -p" + SECRET + " ping")));
//            })
            .waitingFor(Wait.forHealthcheck());

    @Autowired
    private OffersCRUD crud;

    @BeforeEach
    public void setUp() {
        System.out.println(mariadbUrl);
    }

    @Test
    public void shouldNotFail() {
        final Collection<Offer> offers = crud.findAll();
        assertEquals(2, offers.size());
        offers.forEach(x -> System.out.println(x.getDescription()));
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url="
                            + "jdbc:postgresql://" + mariadb.getContainerIpAddress() + ":" + mariadb.getFirstMappedPort() + "/" + DB,
                    "spring.datasource.username=" + "postgres",
                    "spring.datasource.password=" + SECRET
            ).applyTo(configurableApplicationContext.getEnvironment());
        }

    }
}