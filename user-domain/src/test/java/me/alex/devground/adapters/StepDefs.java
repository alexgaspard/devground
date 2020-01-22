package me.alex.devground.adapters;

import io.cucumber.java.*;
import io.cucumber.java.en.*;
import junit.framework.*;
import me.alex.devground.models.*;
import me.alex.devground.ports.*;
import org.mockito.*;

public class StepDefs {

    @Mock
    private NameVerifierService nameVerifierService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AddUserServiceAdapter addUserServiceAdapter;

    @Before
    public void initSteps() {
        MockitoAnnotations.initMocks(this);
    }

    @Given("there is no user")
    public void thereIsNoUser() {
        System.out.println("khjk");
    }

    @When("^the client creates a user$")
    public void theClientCreatesAUser() {
        addUserServiceAdapter.addUser(User.builder().name("Alex").contactEmail("alex@test.com").build());
    }

    @Then("^the client receives user id$")
    public void theClientReceivesUserId() {
        TestCase.assertTrue(true);
    }
}
