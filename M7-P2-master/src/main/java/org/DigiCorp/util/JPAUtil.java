package org.DigiCorp.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

/**
 * JPA Utility class implements a singleton pattern for EntityManagerFactory.
 * JDBC connection parameters to database are defined here.
 */
public class JPAUtil {

    /**
     * single instance of EntityManagerFactory
     */
    private static EntityManagerFactory emf;

    /**
     * Private constructor for singleton implementation
     */
    private JPAUtil() {
    }


    /**
     * retrieves singleton instance of EntityManagerFactory.
     * first call will lazily initialize the factory & return,
     * subsequent calls will retrieve the singleton instance
     *
     * @return
     */
    private static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            Map<String, String> persistenceMap = new HashMap<>();
            persistenceMap.put("jakarta.persistence.jdbc.url",
                    "jdbc:mariadb://localhost:3306/employees");

            emf = Persistence.createEntityManagerFactory("EmployeeService", persistenceMap);
        }
        return emf;
    }

    /**
     * Provides EntityManger instance for use
     *
     * @return new EntityManager instance from the factory
     */
    public static EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }

}
