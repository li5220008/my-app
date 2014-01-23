package ssdb.conf;

import org.apache.commons.pool.impl.GenericObjectPool.Config;

/**
 * Desc:
 * User: weiguili(li5220008@gmail.com)
 * Date: 14-1-21
 * Time: 下午2:30
 */

/**
 * Subclass of org.apache.commons.pool.impl.GenericObjectPool.Config that
 * includes getters/setters so it can be more easily configured by Spring and
 * other IoC frameworks.
 *
 * Spring example:
 *
 * <bean id="SSDBConfig" class="ssdb.conf.SSDBPoolConfig"> <property
 * name="testWhileIdle" value="true"/> </bean>
 *
 * <bean id="SSDBPool" class="ssdb.conf.SSDB.SSDBPool"
 * destroy-method="destroy"> <constructor-arg ref="SSDBConfig" />
 * <constructor-arg value="localhost" /> <constructor-arg type="int"
 * value="8888" /> </bean>
 *
 * For information on parameters refer to:
 *
 * http://commons.apache.org/pool/apidocs/org/apache/commons/pool/impl/
 * GenericObjectPool.html
 */
public class SSDBPoolConfig extends Config {
    //加入SSDB一些默认值
    public static final String DEFAULT_HOST = "192.168.98.250";
    public static final int DEFAULT_PORT = 9966;
    public static final int DEFAULT_TIMEOUT = 2000;
    public SSDBPoolConfig() {
        // defaults to make your life with connection pool easier :)
        setTestWhileIdle(true);
        setMinEvictableIdleTimeMillis(60000);
        setTimeBetweenEvictionRunsMillis(30000);
        setNumTestsPerEvictionRun(-1);
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public long getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }

    public byte getWhenExhaustedAction() {
        return whenExhaustedAction;
    }

    public void setWhenExhaustedAction(byte whenExhaustedAction) {
        this.whenExhaustedAction = whenExhaustedAction;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public long getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(
            long timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public int getNumTestsPerEvictionRun() {
        return numTestsPerEvictionRun;
    }

    public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun) {
        this.numTestsPerEvictionRun = numTestsPerEvictionRun;
    }

    public long getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public long getSoftMinEvictableIdleTimeMillis() {
        return softMinEvictableIdleTimeMillis;
    }

    public void setSoftMinEvictableIdleTimeMillis(
            long softMinEvictableIdleTimeMillis) {
        this.softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
    }

}
