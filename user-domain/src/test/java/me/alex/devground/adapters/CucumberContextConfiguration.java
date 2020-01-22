package me.alex.devground.adapters;

import io.cucumber.java.*;
import me.alex.devground.ports.*;
import org.mockito.*;

public class CucumberContextConfiguration {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserServiceAdapter getUserServiceAdapter;

    @Before
    public void setup_cucumber_spring_context() {
        MockitoAnnotations.initMocks(this);
    }
}