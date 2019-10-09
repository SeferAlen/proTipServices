package com.protip.proTipServices.listeners;

import com.protip.proTipServices.model.Role;
import com.protip.proTipServices.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class DBListener {

    @Autowired
    Logger logger;
    @Autowired
    RoleRepository roleRepository;

    @EventListener
    public void dbSeeder(ContextRefreshedEvent event) {
        seedRoleTable();
    }

    private void seedRoleTable() {

        final List<Role> roles = roleRepository.findAll();

        if(roles == null || roles.size() <= 0) {
            roles.add(new Role("Admin"));
            roles.add(new Role("User"));

            logger.info("Users Seeded");

        } else {
            logger.info("Users Seeding Not Required");

        }
    }
}
