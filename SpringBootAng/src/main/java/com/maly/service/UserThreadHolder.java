package com.maly.service;

import com.maly.dto.User;

public class UserThreadHolder {
    public static ThreadLocal<User>  userDetails = new ThreadLocal<>();
}
