package me.alex.devground.adapters;

import me.alex.devground.models.*;
import me.alex.devground.ports.*;
import org.junit.*;
import org.mockito.*;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GetUserServiceAdapterTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserServiceAdapter getUserServiceAdapter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllSavedUsers_callsTheUserRepositoryTo() {
        when(userRepository.getUsers()).thenReturn(Collections.emptyList());

        getUserServiceAdapter.getAllUsers();

        verify(userRepository).getUsers();
    }

    @Test
    public void getAllSavedUsers_whenThereAreNoSavedUsers_returnsAnEmptyListOfUsers() {
        when(userRepository.getUsers()).thenReturn(Collections.emptyList());

        List<User> returnedUsers = getUserServiceAdapter.getAllUsers();

        assertEquals(0, returnedUsers.size());

        verify(userRepository).getUsers();
    }

    @Test
    public void getAllUsers_whenThereAreSavedUsers_returnsAPopulatedListOfUsers() {
        User user1 = User.builder().id(1).name("A name 1").build();
        User user2 = User.builder().id(2).name("A name 2").build();
        User user3 = User.builder().id(3).name("A name 3").build();

        List<User> savedUsers = Arrays.asList(user1, user2, user3);
        when(userRepository.getUsers()).thenReturn(savedUsers);

        List<User> returnedUsers = getUserServiceAdapter.getAllUsers();

        assertEquals(3, returnedUsers.size());
        assertEquals(returnedUsers, savedUsers);

        verify(userRepository).getUsers();
    }

    @Test
    public void getAUser_callsTheUserRepository() {
        int userId = 1;
        User retrievedUser = User.builder().id(userId).name("User name").build();

        when(userRepository.getUser(userId)).thenReturn(Optional.of(retrievedUser));

        getUserServiceAdapter.getUser(userId);

        verify(userRepository).getUser(userId);
    }

    @Test
    public void getAUser_whenTheUserIdDoesNotMatch_returnsNoUser() {
        int userId = 1;
        when(userRepository.getUser(userId)).thenReturn(Optional.empty());

        Optional<User> returnedUser = getUserServiceAdapter.getUser(userId);

        assertFalse(returnedUser.isPresent());

        verify(userRepository).getUser(userId);
    }

    @Test
    public void getAUser_whenTheUserIdMatches_returnsTheUser() {
        int userId = 1;
        User retrievedUser = User.builder().id(userId).name("User name").build();

        when(userRepository.getUser(userId)).thenReturn(Optional.of(retrievedUser));

        Optional<User> returnedUser = getUserServiceAdapter.getUser(userId);

        assertTrue(returnedUser.isPresent());

        verify(userRepository).getUser(userId);
    }

}
