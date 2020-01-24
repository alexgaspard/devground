package me.alex.devground.services;


import com.github.dockerjava.api.model.*;
import me.alex.devground.config.*;
import org.junit.jupiter.api.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.util.*;
import org.springframework.context.*;
import org.springframework.core.env.*;
import org.springframework.test.context.*;
import org.springframework.test.context.junit4.*;
import org.testcontainers.containers.*;
import org.testcontainers.containers.wait.strategy.*;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.*;

import java.util.*;

@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {DemoConfiguration.class})
@SpringBootTest(classes = {ConfigFileApplicationContextInitializer.class, DemoConfiguration.class})
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

    @Container
    public static GenericContainer<?> mariadb = new GenericContainer<>("mymariadb:10.4.11")
            .withExposedPorts(3306)
            .withEnv("MYSQL_ROOT_PASSWORD", SECRET)
//            .waitingFor(Wait.forLogMessage(".*Temporary server started.*", 1));
//            .withCreateContainerCmdModifier(it -> {
//                it.withHealthcheck(new HealthCheck().withTest(Collections.singletonList("mysqladmin -p" + SECRET + " ping")));
//            })
            .waitingFor(Wait.forHealthcheck());

    @Autowired
    private Environment env;

    @Autowired
    private OffersCRUD crud;

    @BeforeEach
    public void setUp()  {
        System.out.println(mariadbUrl);
        System.out.println(mariadbUsername);
        System.out.println(mariadbPassword);
//        String address = mariadb.getContainerIpAddress();
        System.out.println(mariadb.getContainerIpAddress());
        Integer port = mariadb.getFirstMappedPort();
        System.out.println(port);

        Map<String, Object> map = new HashMap();
        for(Iterator it = ((AbstractEnvironment) env).getPropertySources().iterator(); it.hasNext(); ) {
            PropertySource propertySource = (PropertySource) it.next();
            if (propertySource instanceof MapPropertySource) {
                map.putAll(((MapPropertySource) propertySource).getSource());
            }
        }
        map.forEach((key, value) -> System.out.println(key + ": " + value));
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
                    "spring.datasource.password=" + SECRET
            ).applyTo(configurableApplicationContext.getEnvironment());
        }

    }
}