package server.database;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by eugene on 11/19/16.
 */
public class TransactionHolder extends SessionHolder {
    private static final Map<Thread,TransactionHolder> transactionsPool = new ConcurrentHashMap<>();

    public static Map<Thread, TransactionHolder> getTransactionsPool() {
        return transactionsPool;
    }

    public static TransactionHolder getTransactionHolder(){
        TransactionHolder holder = transactionsPool.get(Thread.currentThread());
        if (holder == null) {
            holder = new TransactionHolder(getHolder());
            transactionsPool.put(Thread.currentThread(),holder);
        }
        return holder;
    }

    private Transaction transaction;


    public TransactionHolder(Session session) {
        super(session);
        beginTransaction();
    }

    public TransactionHolder(SessionHolder holder) {
        super(holder);
        beginTransaction();
    }

    private void beginTransaction(){
        transaction = getSession().beginTransaction();
        LOG.debug("Transaction open");
    }

    public void commit(){
        LOG.debug("Transaction commit");
        transaction.commit();
    }

    public void rollback(){
        LOG.debug("Transaction rollback");
        transaction.rollback();
    }

    public Transaction getTransaction() {
        return transaction;
    }

    @Override
    public void close() throws Exception {
        if(transaction.isActive()){
            if(transaction.getRollbackOnly()) {
                LOG.debug("Transaction marked as rollback-only");
                rollback();
            }
            else commit();
        }
    }
}
