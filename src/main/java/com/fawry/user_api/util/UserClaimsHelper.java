package com.fawry.user_api.util;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class UserClaimsHelper {
    private static Long userId;

   private static final ReadWriteLock lock=new ReentrantReadWriteLock();

    public  static Long getClaims()
    {
        lock.readLock().lock();

        Long userDetails2=userId;

        lock.readLock().unlock();

        return userDetails2;

    }
    public  static void setClaims(Long userId)
    {
        lock.writeLock().lock();
        UserClaimsHelper.userId =userId;
        lock.writeLock().unlock();
    }
}
