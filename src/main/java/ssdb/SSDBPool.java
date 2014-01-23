package ssdb;

import com.udpwork.ssdb.SSDB;
import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool.Config;
import ssdb.conf.SSDBPoolConfig;
import ssdb.util.Pool;

/**
 * Desc:
 * User: weiguili(li5220008@gmail.com)
 * Date: 14-1-21
 * Time: 下午3:11
 */
public class SSDBPool extends Pool<SSDB> {

    public SSDBPool(final GenericObjectPool.Config poolConfig){
        this(poolConfig, SSDBPoolConfig.DEFAULT_HOST, SSDBPoolConfig.DEFAULT_PORT, SSDBPoolConfig.DEFAULT_TIMEOUT);
    }

    public SSDBPool(final GenericObjectPool.Config poolConfig,
                     final String host) {
        this(poolConfig, host, SSDBPoolConfig.DEFAULT_PORT, SSDBPoolConfig.DEFAULT_TIMEOUT);
    }

    public SSDBPool(String host, int port) {
        super(new GenericObjectPool.Config(), new SSDBFactory(host, port,
                SSDBPoolConfig.DEFAULT_TIMEOUT));
    }

    public SSDBPool(final Config poolConfig, final String host, int port,
                     int timeout) {
        super(poolConfig, new SSDBFactory(host, port, timeout));
    }

    public SSDBPool(final Config poolConfig,
                     final String host, final int port) {
        this(poolConfig, host, port, SSDBPoolConfig.DEFAULT_TIMEOUT);
    }

    /*public SSDBPool(final GenericObjectPool.Config poolConfig,
                     final String host, final int port, final int timeout) {
        this(poolConfig, host, port, timeout, null);
    }*/

    /**
     * PoolableObjectFactory custom impl.
     */
    private static class SSDBFactory extends BasePoolableObjectFactory {
        private final String host;
        private final int port;
        private final int timeout;
        //private final String password; //暂时没有提供密码功能

        public SSDBFactory(final String host, final int port,
                            final int timeout) {
            super();
            this.host = host;
            this.port = port;
            this.timeout = (timeout > 0) ? timeout : -1;
            //this.password = password;
        }

        public Object makeObject() throws Exception {
            final SSDB SSDB;
            if (timeout > 0) {
                SSDB = new SSDB(this.host, this.port, this.timeout);
            } else {
                SSDB = new SSDB(this.host, this.port);
            }
            return SSDB;
        }

        public void destroyObject(final Object obj) throws Exception {
            if (obj instanceof SSDB) {
                final SSDB SSDB = (SSDB) obj;
                SSDB.close();
            }
        }

        public boolean validateObject(final Object obj) {
            if (obj instanceof SSDB) {
                final SSDB SSDB = (SSDB) obj;
                return true; //这里因为ssdb暂时还没有提供验证链接状态的方法，所以暂时这样处理。
            } else {
                return false;
            }
        }

    }
}
