package server.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by eugene on 11/19/16.
 */
public class SessionHolder implements AutoCloseable{
    private static final Map<Object, SessionHolder> pool = new ConcurrentHashMap<>();

    public static Map<Object, SessionHolder> getPool() {
        return pool;
    }

    public static void renew() throws Exception {
        SessionHolder holder = pool.get(Thread.currentThread());
        if (holder == null) new SessionHolder();
        else renew(holder);
    }

    public static void renew(SessionHolder holder) throws Exception {
        if(holder.session.isOpen()) holder.session.close();
        holder.session = DbHibernate.newSession();
        LOG.debug("Session renewed");
    }

    public static SessionHolder getHolder(){
        return pool.getOrDefault(Thread.currentThread(), new SessionHolder());
    }

    private Session session;
    protected static Logger LOG = LogManager.getLogger(SessionHolder.class);

    private SessionHolder() {
        this(DbHibernate.newSession());
        LOG.debug("New session spawned");
        getPool().putIfAbsent(Thread.currentThread(), this);
        LOG.info(String.format("SessionPool{size=%d}", pool.size()));
    }

    public SessionHolder(Session session) {
        this.session = session;
    }

    public SessionHolder(SessionHolder holder){
        this(holder.getSession());
    }

    public Session getSession() {
        return session;
    }

    @Override
    public void close() throws Exception {
        SessionHolder.renew(this);
    }
}