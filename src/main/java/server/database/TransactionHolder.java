package server.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Created by eugene on 11/19/16.
 */
public class TransactionHolder extends SessionHolder {
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
