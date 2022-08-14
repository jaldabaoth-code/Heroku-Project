package com.wildcodeschool.cerebook;

import static org.assertj.core.api.Assertions.assertThat;
import com.wildcodeschool.cerebook.entity.User;
import com.wildcodeschool.cerebook.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@Rollback(false)
@ActiveProfiles("test")
public class UserRepositoryTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("gerztest");
        user.setPassword("password");
        user.setFirstName("Gersey");
        user.setLastName("Stelmach");
        user.setEmail("gerseystelmach@hotmail.com");
        user.setRole("ROLE_USER");
        User savedUser = userRepository.save(user);
        User existUser = entityManager.find(User.class, savedUser.getId());
        assertThat(user.getUsername()).isEqualTo(existUser.getUsername());
    }
}
