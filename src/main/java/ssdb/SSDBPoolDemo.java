package ssdb;

import com.udpwork.ssdb.Response;
import com.udpwork.ssdb.SSDB;
import ssdb.conf.SSDBPoolConfig;
import ssdb.exceptions.SSDBConnectionException;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Desc:
 * User: weiguili(li5220008@gmail.com)
 * Date: 14-1-21
 * Time: 下午3:55
 */
public class SSDBPoolDemo {
    static SSDBPool pool = new SSDBPool(new SSDBPoolConfig());
    static SSDB ssdb;
    public static void main(String[] args){
        ssdb = pool.getResource();
        try{
            Response resp;
            byte[] b;
            /* kv */
            System.out.println("---- kv -----");

            //ssdb.set("a", "123");
            ssdb.set("a", "122");
            b = ssdb.get("a");
            System.out.println(new String(b));
            ssdb.del("a");
            b = ssdb.get("a");
            ssdb.set("a", "90");
            System.out.println(b);
            long incr = ssdb.incr("a", 10);
            System.out.println("-------increment the number by--------");
            System.out.println(incr);

            resp = ssdb.scan("", "", 10);
            resp.print();
            resp = ssdb.rscan("", "10000", 10);
            resp.print();
            System.out.println("");

            /* hashmap */
            System.out.println("---- hashmap -----");

            ssdb.hset("n", "a", "123");
            b = ssdb.hget("n", "a");
            System.out.println(new String(b));
            ssdb.hdel("n", "a");
            b = ssdb.hget("n", "a");
            System.out.println(b);
            ssdb.hincr("n", "a", 10);
            ssdb.hset("n", "d", "124");
            ssdb.hset("n", "c", "124");
            ssdb.hset("n", "b", "124");


            resp = ssdb.hscan("n", "a", "z", 10);
            //resp = ssdb.hrscan("n", "", "", 10);
            resp.print();
            System.out.println("");

            /* zset */
            System.out.println("---- zset -----");

            double d;
            ssdb.zset("hackers", "Alan Kay", 1940);
            ssdb.zset("hackers", "Richard Stallman", 1953);
            ssdb.zset("hackers", "Yukihiro Matsumoto", 1965);
            ssdb.zset("hackers", "Claude Shannon", 1916);
            ssdb.zset("hackers", "Linus Torvalds", 1999);
            ssdb.zset("hackers", "Alan Turing", 1912);

            ssdb.zset("n", "a", 1);
            d = ssdb.zget("n", "a");
            System.out.println(d);
            ssdb.zdel("n", "a");
            d = ssdb.zget("n", "a");
            System.out.println(d);
            ssdb.zincr("n", "b", 10);

            //resp = ssdb.zscan("hackers", "", Double.valueOf(1912), Double.valueOf(1999), 10);
            resp = ssdb.zscan("test", "", null, Double.MAX_VALUE, 10);
            resp.print();
            System.out.println(resp.items);
            System.out.println(resp.keys);
            System.out.println(resp.raw);
            System.out.println("");

            /* multi */
            ssdb.multi_set("a", "1b", "b", "2b");
            resp = ssdb.multi_get("a", "b");
            resp.print();
            System.out.println("");
        } catch (SSDBConnectionException e) {
            // returnBrokenResource when the state of the object is unrecoverable
            if (null != ssdb) {
                pool.returnBrokenResource(ssdb);
                ssdb = null;
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            /// ... it's important to return the SSDB instance to the pool once you've finished using it
            if (null != ssdb)
                pool.returnResource(ssdb);
        }


        //concurrentTest();
/// ... when closing your application:
        //pool.destroy();
    }

    public static void concurrentTest() {
        Executor threaPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            Runnable task = new Runnable() {
                public void run() {
                    synchronized (this) {
                        SSDB ssdb0 = pool.getResource();
                        System.out.println(Thread.currentThread().getName());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        try {
                            System.out.println(ssdb0);
                        }catch (SSDBConnectionException e) {
                            // returnBrokenResource when the state of the object is unrecoverable
                            if (null != ssdb0) {
                                pool.returnBrokenResource(ssdb0);
                                ssdb0 = null;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (null != ssdb0)
                                //ssdb.close();
                            pool.returnResource(ssdb0);
                        }
                    }
                }
            };
            threaPool.execute(task);
        }
    }
}
