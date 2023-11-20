package fr.alexpado.mareu.repositories;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Optional;

import fr.alexpado.mareu.InjectionStore;
import fr.alexpado.mareu.entities.User;
import fr.alexpado.mareu.interfaces.repositories.UserRepository;

public class UserRepositoryTests {

    private UserRepository repository = null;

    @Before // = @BeforeEach
    public void setup() {

        this.repository = new FakeUserRepository();
    }

    @Test
    public void findAll_returnSomething() {

        assertFalse(this.repository.findAll().isEmpty());
    }

    @Test
    public void findById_returnOne() {
        assertTrue(this.repository.findById(1L).isPresent());
    }

    @Test
    public void findById_returnNone() {
        assertFalse(this.repository.findById(Long.MAX_VALUE).isPresent());
    }

    @Test
    public void findByMail_returnOne() {
        assertTrue(this.repository.findByMail("j.dassin@lamzone.fr").isPresent());
    }

    @Test
    public void findByMail_returnNone() {
        assertFalse(this.repository.findByMail("this-mail@does-not-exists.com").isPresent());
    }

    @Test
    public void save_isEffective() {
        final String mail = "unit@test.com";

        User user = new User(mail);
        this.repository.save(user);

        assertNotNull(user.getId());
        assertTrue(this.repository.findByMail(mail).isPresent());
    }

    @Test
    public void remove_isEffective() {
        final String mail = "j.dassin@lamzone.fr";

        Optional<User> byMail = this.repository.findByMail(mail);
        assertTrue(byMail.isPresent());
        assertEquals(mail, byMail.get().getMail());

        this.repository.remove(byMail.get());

        Optional<User> byMailAfter = this.repository.findByMail(mail);
        assertFalse(byMailAfter.isPresent());

    }

}
