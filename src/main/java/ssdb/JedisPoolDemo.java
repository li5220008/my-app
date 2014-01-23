package ssdb;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;
import ssdb.exceptions.SSDBConnectionException;

import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Desc:
 * User: weiguili(li5220008@gmail.com)
 * Date: 14-1-21
 * Time: 下午5:47
 */
public class JedisPoolDemo {
    static JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
    static Jedis jedis;
    public static void main(String[] args){

        jedis = pool.getResource();
        try {
            /// ... do stuff here ... for example
            jedis.set("foo", "bar");
            String foobar = jedis.get("foo");
            jedis.zadd("sose", 0, "car"); jedis.zadd("sose", 0, "bike");
            Set<String> sose = jedis.zrange("sose", 0, -1);
        } catch (JedisConnectionException e) {
            // returnBrokenResource when the state of the object is unrecoverable
            if (null != jedis) {
                pool.returnBrokenResource(jedis);
                jedis = null;
            }
        } finally {
            /// ... it's important to return the Jedis instance to the pool once you've finished using it
            if (null != jedis)
                pool.returnResource(jedis);
        }
/// ... when closing your application:
        //pool.destroy();

        Executor threaPool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 20; i++) {
            Runnable task = new Runnable() {
                public void run() {
                    synchronized (this) {
                        System.out.println(Thread.currentThread().getName());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            Jedis jedis0 = pool.getResource();
                            System.out.println(jedis0);
                        }catch (SSDBConnectionException e) {
                            // returnBrokenResource when the state of the object is unrecoverable
                            if (null != jedis) {
                                //pool.returnBrokenResource(jedis);
                                jedis = null;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (null != jedis)
                                jedis.disconnect();
                            //pool.returnResource(jedis);
                        }
                    }
                }
            };
            threaPool.execute(task);
        }
    }
}
