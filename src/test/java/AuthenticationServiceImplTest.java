import com.protip.proTipServices.ProTipServicesApplication;
import com.protip.proTipServices.exceptions.PasswordIncorrectException;
import com.protip.proTipServices.exceptions.UserNotFoundException;
import com.protip.proTipServices.model.Login;
import com.protip.proTipServices.repository.LoginRepository;
import com.protip.proTipServices.service.AuthenticationService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

/**
 * Unit tests for AuthenticationService
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProTipServicesApplication.class)
public class AuthenticationServiceImplTest {
    private static final String password = "testPassword";
    private static final String email = "alensefer1990@gmail.com";

    @Autowired
    private AuthenticationService authenticationService;
    @MockBean
    private LoginRepository loginRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @Value("${token.for.test}")
    private String testToken;

    /**
     * Test exceptions being thrown by AuthenticationsService
     */
    @Test
    public void testExceptions() {
        final Login login = new Login();
        login.setUsername(email);
        login.setPassword(password);

        Assertions.assertThrows(NullPointerException.class, () -> authenticationService.loginAndGenerateToken(null));
        Mockito.when(loginRepository.findByUsername(email)).thenReturn(null);
        Assertions.assertThrows(UserNotFoundException.class, () -> authenticationService.loginAndGenerateToken(login));
        Assertions.assertThrows(UserNotFoundException.class, () -> authenticationService.getProTipUser(testToken));
        Mockito.when(loginRepository.findByUsername(email)).thenReturn(login);
        Mockito.when(passwordEncoder.matches(password, login.getPassword())).thenReturn(false);
        Assertions.assertThrows(PasswordIncorrectException.class, () -> authenticationService.loginAndGenerateToken(login));

        verify(loginRepository, times(4)).findByUsername(email);
        verify(passwordEncoder, times(1)).matches(password, password);
    }
}
