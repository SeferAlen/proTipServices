package com.protip.proTipServices.listeners;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
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
    @Autowired
    PasswordEncoder passwordEncoder;

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

    /**
     * Method for creating default user at application start
     */
    private void createDefaultAdminUser() {
        try {
            final Date date = new SimpleDateFormat("yyyy-MM-dd").parse("1990-03-22");

            final ProTipUser defaultUser = new ProTipUser("Alen","Sefer","sefer.alen@yahoo.com", date);
            final Role defaultUserRole = roleRepository.findByName("Admin");
            final Login defaultUserLogin = new Login(defaultUser.getEmail(), passwordEncoder.encode("proTipServicesSeferAlen"), defaultUser, defaultUserRole);

            userRepository.save(defaultUser);
            loginRepository.save(defaultUserLogin);
        } catch (Exception e) {
            throw new RuntimeException("Error while creating default user in database");
        }
    }
}
