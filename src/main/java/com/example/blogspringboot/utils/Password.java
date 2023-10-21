package com.example.blogspringboot.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Password {
    public static void main(String[] args) {
        PasswordEncoder psw = new BCryptPasswordEncoder();
        System.out.println(psw.encode("password"));
    }
}
