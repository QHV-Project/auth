package sansan.auth.AuthUtils;


import org.apache.commons.lang3.RandomStringUtils;

public class AuthUtils {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";

    public static String generatePassword() {
        return RandomStringUtils.random(8, CHARACTERS);
    }
}
