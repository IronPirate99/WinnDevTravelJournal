package com.travelingjournal.util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    
    // Hash a password before saving to DB
    public static String hashPassword(String plainText) {
        return BCrypt.hashpw(plainText, BCrypt.gensalt());
    }

    // Check if a plain text password matches the stored hash
    public static boolean checkPassword(String plainText, String hashed) {
        return BCrypt.checkpw(plainText, hashed);
    }
}