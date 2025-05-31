package jets.spring_boot.utils;

import java.util.regex.Pattern;

public class DataValidator {
    
    private static final Pattern NAME_PATTERN 
            = Pattern.compile("^[a-zA-Z0-9_-]{4,30}$");

    private static final Pattern EMAIL_PATTERN 
            = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    
    private static final Pattern PASSWORD_PATTERN 
            = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).{8,32}$");
    
    private static final Pattern PHONE_PATTERN 
            = Pattern.compile("^(?:\\+20|0)(10|11|12|15)\\d{8}$");
    
    private DataValidator() {
        throw new AssertionError("Do not create object");
    }
    
    public static void validateId(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id must start from 1");
        }
    }
    
    // Should catch.
    public static void validateString(String prefix, String string) {
        if (string == null) {
            throw new IllegalArgumentException(prefix + " cannot be null");
        }
        
        if (string.isBlank()) {
            throw new IllegalArgumentException(prefix + " cannot be empty");
        }
    }

    public static void validateTitle(String title) {
        validateString("Title", title);

        if (title.length() < 4
        ||  title.length() > 30) {
            throw new IllegalArgumentException("Title must be between 4 - 30 characters");
        }
    }
    
    public static void validateName(String name) {
        validateString("Name", name);
        
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new IllegalArgumentException("Name must be between 4 - 30 characters, "
                    + "contains only alphanumeric, - and _");
        }
    }
    
    public static void validateEmail(String email) {
        validateString("Email", email);

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Email must be a valid email address");
        }
    }
    
    public static void validatePassword(String password, String prefix) {
        if (prefix == null) {
            prefix = "Password";
        }
        validateString(prefix, password);
        
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException("Password must between 8 - 32 characters, "
                    + "it should include a lowercase, uppercase, and a digit");
        }
    }

    @SuppressWarnings("unused")
    public static void validatePhone(String phone) {
        validateString("Phone", phone);
        
        if (!PHONE_PATTERN.matcher(phone).matches()) {
            throw new IllegalArgumentException("Phone must be a valid egyptian phone");
        }
    }

    public static void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
    }
}
