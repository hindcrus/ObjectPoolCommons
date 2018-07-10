package com.pool.commons.chetan.parser;

public class XmlParser<E, T> implements Parser<E, T> {
    private Exception exception;
    @Override
    public void parse(E elementToBeParsed, T result) throws Exception {
        try {
            System.out.println("[" + Thread.currentThread().getName()+ "]: Parser Instance:" + this);
            // Do some real parsing stuff.
        } catch(Exception e) {
            this.exception = e;
            e.printStackTrace(System.err);
            throw e;
        }
    }
    @Override
    public boolean isValid() {
        return this.exception == null;
    }
    @Override
    public void reset() {
        this.exception = null;
    }
}
