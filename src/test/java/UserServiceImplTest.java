import com.protip.proTipServices.ProTipServicesApplication;
import com.protip.proTipServices.config.Config;
import com.protip.proTipServices.model.Login;
import com.protip.proTipServices.model.ProTipUser;
import com.protip.proTipServices.model.Role;
import com.protip.proTipServices.repository.LoginRepository;
import com.protip.proTipServices.repository.UserRepository;
import com.protip.proTipServices.service.UserService;
import com.protip.proTipServices.service.UserServiceImpl;
import com.protip.proTipServices.utility.UserCreateStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProTipServicesApplication.class)
public class UserServiceImplTest {

    final ProTipUser proTipUser1 = new ProTipUser("Alen","Sefer", "alensefer1990@gmail.com", new Date());
    final ProTipUser proTipUser2 = new ProTipUser("Pero","Peric", "peroperic@gmail.com", new Date());
    final Login login1 = new Login(proTipUser1.getEmail(), "test", proTipUser1, new Role("test"));
    final List<ProTipUser> users = new LinkedList<ProTipUser>();

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public UserService employeeService() {
            return new UserServiceImpl();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    LoginRepository loginRepository;

    @MockBean
    UserRepository userRepository;

    @Before
    public void setUp() {
        users.add(proTipUser1);
        users.add(proTipUser2);

        Mockito.when(userRepository.findAll()).thenReturn(users);
        Mockito.when(loginRepository.findByUsername("alensefer1990@gmail.com")).thenReturn(login1);
        Mockito.when(userRepository.findAll()).thenReturn(users);
    }

    @Test
    public void testGetUsers() {
        Assert.assertEquals(2, userService.getUsers().size());
    }

    @Test
    public void testFindByUsername() {
        Assert.assertEquals(login1, userService.findByUsername("alensefer1990@gmail.com"));
    }

    @Test
    public void testCreateUser() {
        Assert.assertEquals(UserCreateStatus.ALREADY_EXIST, userService.createUser(proTipUser1, "test", new Role("test")));
        final ProTipUser testUser = new ProTipUser("Pajo", "Patak", "pajoPatak@gmail.com", new Date());

        Assert.assertEquals(UserCreateStatus.CREATED, userService.createUser(testUser, "test", new Role("test")));
    }
}
