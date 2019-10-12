package com.protip.proTipServices.listeners;

import com.protip.proTipServices.model.Login;
import com.protip.proTipServices.model.ProTipUser;
import com.protip.proTipServices.model.Role;
import com.protip.proTipServices.repository.LoginRepository;
import com.protip.proTipServices.repository.RoleRepository;
import com.protip.proTipServices.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Listener for database related events
 */
@Component
public class DBListener {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    LoginRepository loginRepository;
    @Autowired
    UserRepository userRepository;

    /**
     * Db seeder
     *
     * @param event {@link ContextRefreshedEvent} event which occurred
     */
    @EventListener
    public void dbSeeder(ContextRefreshedEvent event) {
        seedRoleTable();
        createDefaultAdminUser();
    }

    /**
     * Method for seeding database table at application start
     */
    private void seedRoleTable() {

        final List<Role> roles = roleRepository.findAll();

        if(roles == null || roles.size() <= 0) {
            roleRepository.save(new Role("Admin"));
            roleRepository.save(new Role("User"));

        } else {
        }
    }

    private void createDefaultAdminUser() {

        final ProTipUser defaultUser = new ProTipUser("Alen","Sefer","sefer.alen@yahoo.com",new Date("1990-03-22"));
        final Role defaultUserRole = roleRepository.findByName("Admin");
        final Login defaultUserLogin = new Login(defaultUser.getEmail(), "proTipServicesSeferAlen", defaultUser, defaultUserRole);

        userRepository.save(defaultUser);
        loginRepository.save(defaultUserLogin);
    }
}
