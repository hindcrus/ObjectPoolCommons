package com.pool.commons.chetan;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.pool.commons.chetan.parser.Parser;

public class ParserPool<E, T> extends GenericObjectPool<Parser<E, T>>{
    /**
     * Constructor.
     * 
     * It uses the default configuration for pool provided by
     * apache-commons-pool2.
     * 
     * @param factory
     */
    public ParserPool(PooledObjectFactory<Parser<E, T>> factory) {
        super(factory);
    }
    /**
     * Constructor.
     * 
     * This can be used to have full control over the pool using configuration
     * object.
     * 
     * @param factory
     * @param config
     */
    public ParserPool(PooledObjectFactory<Parser<E, T>> factory,
            GenericObjectPoolConfig config) {
        super(factory, config);
    }
}
