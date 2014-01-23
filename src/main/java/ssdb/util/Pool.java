package ssdb.util;

import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import ssdb.exceptions.SSDBConnectionException;
import ssdb.exceptions.SSDBException;

/**
 * Desc:
 * User: weiguili(li5220008@gmail.com)
 * Date: 14-1-21
 * Time: 下午2:00
 */
public abstract class Pool<T> {
    private final GenericObjectPool internalPool;
    public Pool(final GenericObjectPool.Config poolConfig,
                PoolableObjectFactory factory) {
        this.internalPool = new GenericObjectPool(factory, poolConfig);
    }
    @SuppressWarnings("unchecked")
    public T getResource() {
        try {
            return (T) internalPool.borrowObject();
        } catch (Exception e) {
            throw new SSDBConnectionException(
                    "Could not get a resource from the pool", e);
        }
    }

    public void returnResource(final T resource) {
        try {
            internalPool.returnObject(resource);
        } catch (Exception e) {
            throw new SSDBException(
                    "Could not return the resource to the pool", e);
        }
    }

    public void returnBrokenResource(final T resource) {
        try {
            internalPool.invalidateObject(resource);
        } catch (Exception e) {
            throw new SSDBException(
                    "Could not return the resource to the pool", e);
        }
    }

    public void destroy() {
        try {
            internalPool.close();
        } catch (Exception e) {
            throw new SSDBException("Could not destroy the pool", e);
        }
    }
}
