package com.worldstone.worldengine.database;

import com.worldstone.worldengine.WorldEngine;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {

    public static String getUserPasswordHash(String email, String rawPassword) {
        // Get byte array for input bytes
        byte[] emailBytes = email.getBytes();
        byte[] passwordBytes = rawPassword.getBytes();
        byte[] infoBytes = new byte[emailBytes.length + passwordBytes.length];
        for (int i = 0; i < infoBytes.length; i++) {
            if (i < emailBytes.length) {
                infoBytes[i] = emailBytes[i];
            }
            else {
                infoBytes[i] = passwordBytes[i - emailBytes.length];
            }
        }

        // Get byte array for configured salt
        String salt = WorldEngine.INSTANCE.getConfig().getHashSalt();
        byte[] saltBytes = salt.getBytes();

        // Shuffle the arrays together (non-random)
        byte[] shuffledBytes = new byte[infoBytes.length + saltBytes.length];
        int lastInfoIndex = 0;
        int lastSaltIndex = 1;
        for (int i = 0; i < shuffledBytes.length; i++) {
            boolean infoBytesExhausted = (i / 2) >= infoBytes.length;
            boolean saltBytesExhausted = (i - 1) / 2 >= saltBytes.length;
            if (i % 2 == 0 && !infoBytesExhausted) {
                shuffledBytes[i] = infoBytes[i / 2];
                lastInfoIndex = i / 2;
            }
            else if (i % 2 == 1 && !saltBytesExhausted) {
                shuffledBytes[i] = saltBytes[(i - 1) / 2];
                lastSaltIndex = (i - 1) / 2;
            }
            else if (infoBytesExhausted) {
                shuffledBytes[i] = saltBytes[++lastSaltIndex];
            }
            else if (saltBytesExhausted) {
                shuffledBytes[i] = infoBytes[++lastInfoIndex];
            }
        }

        // Get hash bytes
        String rawHashInput = new String(shuffledBytes);
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] hashBytes = digest.digest(rawHashInput.getBytes(StandardCharsets.UTF_8));
        StringBuilder hashStringBuilder = new StringBuilder();
        for (byte b : hashBytes) {
            hashStringBuilder.append(String.format("%02x", b));
        }
        return hashStringBuilder.toString();
    }

}
