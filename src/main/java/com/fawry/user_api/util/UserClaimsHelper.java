package com.fawry.user_api.util;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


public class UserClaimsHelper {
    private static UserDetails userDetails;

   private static final ReadWriteLock lock=new ReentrantReadWriteLock();

    public  static UserDetails getClaims()
    {
        lock.readLock().lock();

        UserDetails userDetails2=userDetails;

        lock.readLock().unlock();

        return userDetails2;

    }
    public  static void setClaims(UserDetails userDetails)
    {
        lock.writeLock().lock();
        UserClaimsHelper.userDetails =userDetails;
        lock.writeLock().unlock();
    }
}
