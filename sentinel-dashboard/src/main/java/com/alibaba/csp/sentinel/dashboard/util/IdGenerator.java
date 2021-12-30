package com.alibaba.csp.sentinel.dashboard.util;

/**
 * @author rodbate
 * @since 2019/04/20 12:36
 */
public interface IdGenerator<T> {

    /**
     * get next global unique id
     *
     * @return unique id
     */
    T nextId();

}
