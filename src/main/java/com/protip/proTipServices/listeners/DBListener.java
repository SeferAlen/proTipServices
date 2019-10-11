package com.protip.proTipServices.listeners;

import com.protip.proTipServices.model.Role;
import com.protip.proTipServices.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Listener for database related events
 */
@Component
public class DBListener {

    @Autowired
    RoleRepository roleRepository;

    /**
     * Db seeder
     *
     * @param event {@link ContextRefreshedEvent} event which occurred
     */
    @EventListener
    public void dbSeeder(ContextRefreshedEvent event) {
        seedRoleTable();
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
}
