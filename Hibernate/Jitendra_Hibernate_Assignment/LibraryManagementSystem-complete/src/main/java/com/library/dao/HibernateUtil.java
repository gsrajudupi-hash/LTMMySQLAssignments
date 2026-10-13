package com.library.dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class HibernateUtil {
    private static final SessionFactory FACTORY = build();

    private HibernateUtil() {
    }

    private static SessionFactory build() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return FACTORY;
    }

    public static void shutdown() {
        if (!FACTORY.isClosed()) FACTORY.close();
    }
}
