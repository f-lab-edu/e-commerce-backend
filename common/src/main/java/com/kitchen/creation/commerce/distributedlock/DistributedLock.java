package com.kitchen.creation.commerce.distributedlock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Redisson Distributed Lock annotation
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {

    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * wait time to obtain the lock
     */
    long waitTime() default 10L;

    /**
     * when the lock is leased,
     * unlock after the lease time
     */
    long leaseTime() default 3L;
}