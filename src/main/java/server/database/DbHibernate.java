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

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by eugene on 10/10/16.
 */
@SuppressWarnings("DefaultFileTemplate")
public class DbHibernate {
    private static Configuration configuration;
    private static SessionFactory factory;

    static {
        configuration = new Configuration().configure();
        factory = configuration.buildSessionFactory();
    }

    public static Session newSession(){
        return factory.openSession();
    }

    public static void doTransactional(Session session,Consumer<Session> consumer)
            throws TransactionalError {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            consumer.accept(session);
            transaction.commit();
        }
        catch (RuntimeException e){
            if(transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            throw new TransactionalError(e.getMessage(),e);
        }
    }

    public static <T> T getTransactional(Session session, Function<Session,T> function)
            throws TransactionalError {
        Transaction transaction = null;
        T result  = null;

        try {
            transaction = session.beginTransaction();
            result = function.apply(session);
            transaction.commit();
        }
        catch (RuntimeException e){
            if(transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            throw new TransactionalError(e.getMessage(),e);
        }
        return result;
    }

    private DbHibernate(){}
}
