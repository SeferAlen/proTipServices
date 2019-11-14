import com.protip.proTipServices.ProTipServicesApplication;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProTipServicesApplication.class)
public class UserServiceImplTest {
    private static final String firstName1 = "Alen";
    private static final String firstName2 = "Pero";
    private static final String firstName3 = "Pajo";
    private static final String lastName1 = "Sefer";
    private static final String lastName2 = "Perić";
    private static final String lastName3 = "Patak";
    private static final String email1 = "alensefer1990@gmail.com";
    private static final String email2 = "peroperic@gmail.com";
    private static final String email3 = "pajoPatak@gmail.com";
    private static final String test = "test";

    final ProTipUser proTipUser1 = new ProTipUser(firstName1,lastName1, email1, new Date(), new Date());
    final ProTipUser proTipUser2 = new ProTipUser(firstName2,lastName2, email2, new Date(), new Date());
    final Login login1 = new Login(proTipUser1.getEmail(), test, proTipUser1, new Role(test));
    final List<ProTipUser> users = new ArrayList<>();

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
        Mockito.when(loginRepository.findByUsername(email1)).thenReturn(login1);
        Mockito.when(userRepository.findAll()).thenReturn(users);
    }

    @Test
    public void testGetUsers() {
        Assert.assertEquals(2, userService.getUsers().size());
    }

    @Test
    public void testFindByUsername() {
        Assert.assertEquals(login1, userService.findByUsername(email1));
    }

    @Test
    public void testCreateUser() {
        Assert.assertEquals(UserCreateStatus.ALREADY_EXIST, userService.createUser(proTipUser1, test, new Role(test)));
        final ProTipUser testUser = new ProTipUser(firstName3, lastName3, email3, new Date(), new Date());

        Assert.assertEquals(UserCreateStatus.CREATED, userService.createUser(testUser, test, new Role(test)));
    }
}
