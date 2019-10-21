package com.protip.proTipServices.listeners;

import com.protip.proTipServices.model.ProTipUser;
import com.protip.proTipServices.model.Role;
import com.protip.proTipServices.repository.RoleRepository;
import com.protip.proTipServices.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
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
    UserService userService;
    @Value("${default.user.password}")
    private String defaultPassword;

    /**
     * Db seeder
     *
     * @param event {@link ContextRefreshedEvent} event which occurred
     */
    @EventListener
    public void dbSeeder(final ContextRefreshedEvent event) {
        seedRoleTable();
        createDefaultAdminUser();
    }

    /**
     * Method for seeding database table at application start
     */
    private void seedRoleTable() {

        final List<Role> roles = roleRepository.findAll();

        if(roles == null || roles.size() <= 0) {
            roleRepository.save(new Role("ADMIN"));
            roleRepository.save(new Role("USER"));

        } else {
            // Nothing do to here
        }
    }

    /**
     * Method for creating default user at application start
     */
    private void createDefaultAdminUser() {
        try {
            final Date date = new SimpleDateFormat("yyyy-MM-dd").parse("1990-03-22");

            final ProTipUser defaultUser = new ProTipUser("Alen","Sefer","sefer.alen@yahoo.com", date);
            final Role defaultUserRole = roleRepository.findByName("ADMIN");

            userService.createUser(defaultUser, defaultPassword, defaultUserRole);
        } catch (final Exception e) {
            throw new RuntimeException("Error while creating default user in database");
        }
    }
}
