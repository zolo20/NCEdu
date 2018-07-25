package ru.ars.ncedu.task9.requestdatabase;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class SessionHibernate {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private SessionHibernate() {}

    private static SessionFactory buildSessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            return new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);

            throw new ExceptionInInitializerError("Initial SessionFactory failed" + e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
