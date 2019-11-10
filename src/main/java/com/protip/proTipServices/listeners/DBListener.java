package com.protip.proTipServices.listeners;

import com.protip.proTipServices.model.MessageType;
import com.protip.proTipServices.model.ProTipUser;
import com.protip.proTipServices.model.Role;
import com.protip.proTipServices.repository.MessageTypeRepository;
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
    private final String MESSAGE_TYPE_MESSAGE = com.protip.proTipServices.utility.MessageType.MESSAGE.toString();
    private final String MESSAGE_TYPE_NOTIFICATION = com.protip.proTipServices.utility.MessageType.NOTIFICATION.toString();
    private final String ROLE_ADMIN = "ADMIN";
    private final String ROLE_USER = "USER";
    private final String DEFAULT_USER_FIRST_NAME = "Alen";
    private final String DEFAULT_USER_LAST_NAME = "Sefer";
    private final String DEFAULT_USER_EMAIL = "alensefer1990@gmail.com";
    private final String ERROR_MESSAGE = "Error while creating default user in database";
    private final String DATE = "1990-03-22";
    private final String DATE_PARSE_FORMAT = "yyyy-MM-dd";

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    MessageTypeRepository messageTypeRepository;
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
        seedMessageTypeTable();
        createDefaultAdminUser();
    }

    /**
     * Method for seeding database table at application start
     */
    private void seedRoleTable() {

        final List<Role> roles = roleRepository.findAll();

        if(roles == null || roles.size() <= 0) {
            roleRepository.save(new Role(ROLE_ADMIN));
            roleRepository.save(new Role(ROLE_USER));

        } else {
            // Nothing do to here
        }
    }

    /**
     * Method for seeding database table at application start
     */
    private void seedMessageTypeTable() {
        final List<MessageType> messageTypes = messageTypeRepository.findAll();

        if(messageTypes == null || messageTypes.size() <= 0) {
            messageTypeRepository.save(new MessageType(MESSAGE_TYPE_MESSAGE));
            messageTypeRepository.save(new MessageType(MESSAGE_TYPE_NOTIFICATION));

        } else {
            // Nothing do to here
        }
    }

    /**
     * Method for creating default user at application start
     */
    private void createDefaultAdminUser() {
        try {
            final Date date = new SimpleDateFormat(DATE_PARSE_FORMAT).parse(DATE);

            final ProTipUser defaultUser = new ProTipUser(DEFAULT_USER_FIRST_NAME,DEFAULT_USER_LAST_NAME,DEFAULT_USER_EMAIL, date);
            final Role defaultUserRole = roleRepository.findByName(ROLE_ADMIN);

            userService.createUser(defaultUser, defaultPassword, defaultUserRole);
        } catch (final Exception e) {
            throw new RuntimeException(ERROR_MESSAGE);
        }
    }
}
