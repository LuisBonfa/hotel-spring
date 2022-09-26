package com.alten.hotel.common.utils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Random;

/**
 * Used to hash and control security of passwords
 *
 * @author luis.bonfa
 */
public class Security {

    private Security() {
    }

    private static final int PBKDF2_CYCLES = 32768;
    public static final int PBKDF2_SHA512_LENGTH = 512;

    public static byte[] hashPassword(final char[] password, final byte[] salt, final int iterations, final int keyLength)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        var skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        var spec = new PBEKeySpec(password, salt, iterations, keyLength);
        SecretKey key = skf.generateSecret(spec);
        return key.getEncoded();
    }

    public static String hash(String password, String salt) {
        try {
            byte[] bsalt = Base64.decodeBase64(salt.getBytes());

            byte[] hash = hashPassword(password.toCharArray(), bsalt, PBKDF2_CYCLES, PBKDF2_SHA512_LENGTH);
            var base64Salt = Base64.encodeBase64String(bsalt);
            var base64Hash = Base64.encodeBase64String(hash);

            return base64Salt + ":" + base64Hash;
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error Verifying Password", e);
        }
    }

    public static String hash(String password) {
        try {
            var salt = new byte[32];
            new Random().nextBytes(salt);

            byte[] hash = hashPassword(password.toCharArray(), salt, PBKDF2_CYCLES, PBKDF2_SHA512_LENGTH);
            var base64Salt = Base64.encodeBase64String(salt);
            var base64Hash = Base64.encodeBase64String(hash);

            return base64Salt + ":" + base64Hash;
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error Generating Password", e);
        }
    }

    public static boolean validate(String plainPassword, String hashedPassword) {
        try {
            String[] hashParts = hashedPassword.split(":");
            String hashToCompare = hash(plainPassword, Arrays.toString(hashParts));

            return hashToCompare.equals(hashedPassword);
        } catch (Exception e) {
            return false;
        }
    }
}
