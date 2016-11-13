package server.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.jetbrains.annotations.TestOnly;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by eugene on 10/10/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class DbHibernate {
    private static SessionFactory factory;

    static {
        Configuration configuration = new Configuration().configure();
        factory = configuration.buildSessionFactory();
    }

    @TestOnly
    public static SessionFactory getFactory() {
        return factory;
    }

    public static Session newSession(){
        return factory.openSession();
    }

    public static void doTransactional(Consumer<Session> operation) throws TransactionalError {
        Transaction transaction = null;
        try (Session session = newSession()) {
            transaction = session.beginTransaction();
            operation.accept(session);
            transaction.commit();
        }
        catch (RuntimeException e){
            if (transaction != null) transaction.rollback();
            throw new TransactionalError(e);
        }
    }

    public static <T> T getTransactional(Function<Session,T> operation) throws TransactionalError {
        Transaction transaction = null;
        try (Session session = newSession()) {
            transaction = session.beginTransaction();
            T result = operation.apply(session);
            transaction.commit();
            return result;
        }
        catch (RuntimeException e){
            if (transaction != null) transaction.rollback();
            throw new TransactionalError(e);
        }
    }

    private DbHibernate(){}
}
