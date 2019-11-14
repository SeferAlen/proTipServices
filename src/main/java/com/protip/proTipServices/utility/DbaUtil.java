package com.protip.proTipServices.utility;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

/**
 * Utility method for db related actions
 */
public class DbaUtil {
    private static final String NULL_EXCEPTION = "Entity passed for initialization is null";

    /**
     * Generic method for getting real object from entity needed because lazy fetch
     *
     * @param entity {@link T} the entity containing lazy fetch object
     * @return {@link T}       the real object
     */
    public static <T> T initializeAndUnproxy(T entity) {
        if (entity == null) {
            throw new
                    NullPointerException(NULL_EXCEPTION);
        }

        Hibernate.initialize(entity);
        if (entity instanceof HibernateProxy) {
            entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
        }

        return entity;
    }
}
