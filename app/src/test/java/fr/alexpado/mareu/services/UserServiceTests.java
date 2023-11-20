package fr.alexpado.mareu.services;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import fr.alexpado.mareu.InjectionStore;
import fr.alexpado.mareu.entities.User;
import fr.alexpado.mareu.repositories.FakeUserRepository;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UserServiceTests {

    private UserService service = null;

    @Before // = @BeforeEach
    public void setup() {

        this.service = new UserService(new FakeUserRepository());
    }

    @Test
    public void getUsers_returnSomething() {

        assertFalse(this.service.getUsers().isEmpty());
    }

    @Test
    public void findUser_returnOne() {
        assertTrue(this.service.findUser("j.dassin@lamzone.fr").isPresent());
    }

    @Test
    public void findUser_returnNone() {
        assertFalse(this.service.findUser("this-mail@does-not-exists.com").isPresent());
    }

    @Test
    public void createUser_returnsOne() {
        final String mail = "unit@test.com";

        User user = this.service.createUser(mail);

        assertNotNull(user.getId());
        assertEquals(mail, user.getMail());
    }

}