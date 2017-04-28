package com.hrp.utils.dataSource;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * DynamicTransactionManager
 *
 * @author KVLT
 * @date 2017-03-14.
 */
public class DynamicTransactionManager implements PlatformTransactionManager {

    private Map<String, PlatformTransactionManager> targetTransactionManagers = new HashMap<String, PlatformTransactionManager>();

    protected PlatformTransactionManager getTargetTransactionManager() {
        String context = DatabaseContextHolder.getCustomerType()+"TransactionManager";
        return targetTransactionManagers.get(context);
    }

    public void setTargetTransactionManagers(Map<String, PlatformTransactionManager> targetTransactionManagers) {
        this.targetTransactionManagers = targetTransactionManagers;
    }

    public void commit(TransactionStatus arg0) throws TransactionException {
        getTargetTransactionManager().commit(arg0);
    }

    public TransactionStatus getTransaction(TransactionDefinition arg0)
            throws TransactionException {
        return getTargetTransactionManager().getTransaction(arg0);
    }

    public void rollback(TransactionStatus arg0) throws TransactionException {
        getTargetTransactionManager().rollback(arg0);
    }
}
