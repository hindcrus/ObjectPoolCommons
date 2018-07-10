package com.pool.commons.chetan;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.pool.commons.chetan.parser.Parser;
import com.pool.commons.chetan.poolFactory.ParserFactory;

public class Main {


public static void main(String args[])
{
	GenericObjectPoolConfig config = new GenericObjectPoolConfig();
    config.setMaxIdle(1);
    config.setMaxTotal(1);
    /*---------------------------------------------------------------------+
    |TestOnBorrow=true --> To ensure that we get a valid object from pool  |
    |TestOnReturn=true --> To ensure that valid object is returned to pool |
    +---------------------------------------------------------------------*/
    config.setTestOnBorrow(true);
    config.setTestOnReturn(true);
    ParserPool<String, String> pool = new ParserPool<String, String>(new ParserFactory<String, String>(), config);
    AtomicInteger count = new AtomicInteger(0);
    try {
        int limit = 10;
        ExecutorService es = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(limit));
        for (int i=0; i<limit; i++) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    Parser<String, String> parser = null;
                    try {
                        parser = pool.borrowObject();
                        count.getAndIncrement();
                        parser.parse(null, null);
                    } catch (Exception e) {
                        e.printStackTrace(System.err);
                    } finally {
                        if (parser != null) {
                            pool.returnObject(parser);
                        }
                    }
                }
            };
            es.submit(r);
        }
        es.shutdown();
        try {
            es.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException ignored) {}
        System.out.println("Pool Stats:\n Created:[" + pool.getCreatedCount() + "], Borrowed:[" + pool.getBorrowedCount() + "]");
//        Assert.assertEquals(limit, count.get());
//        Assert.assertEquals(count.get(), pool.getBorrowedCount());
//        Assert.assertEquals(1, pool.getCreatedCount());
    } catch (Exception ex) {
        //fail("Exception:" + ex);
    }
}
}
