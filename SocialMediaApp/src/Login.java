import java.util.Objects;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Login {
    private String email;
    private String password;
    
    Login(String email, String password){
        Objects.requireNonNull(email, "Email cannot be null");
        Objects.requireNonNull(password, "Password cannot be null");
        
        // Validate email format
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Password have a mix of uppercase and lowercase characters, numbers, and special characters");
        }
        
        this.email = email;
        this.password = password;
    }
    
    private String getEmail(){
        return email;
    }
    
    private String getPassword(){
        return password;
    }
    
    private void setEmail(String email) {
        Objects.requireNonNull(email, "Email cannot be null");
        
        // Validate email format
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }
        
        this.email = email;
    }
    
    private void setPassword(String password) {
        Objects.requireNonNull(password, "Password cannot be null");
        // Validate password (optional)
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("Password have a mix of uppercase and lowercase characters, numbers, and special characters");
        }
        this.password = password;
    }
    
    public boolean checkPassword(String inputPassword){
        return this.password.equals(inputPassword);
    }
    
    // Email validation method using InternetAddress
    private boolean isValidEmail(String email) {
        try {
            InternetAddress emailAddress = new InternetAddress(email);
            // Ensure that the parsed email address has a valid format
            emailAddress.validate();
            return true;
        } catch (AddressException e) {
            // If an AddressException is thrown, the email address is invalid
            return false;
        }
    }
    private boolean isValidPassword(String password) {
        // Check if the password meets the specified criteria
        if (password.length() < 8) {
            return false; // Password length is less than 8 characters
        }
        
        boolean hasUppercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;
        String specialCharacters = "!@#$%^&*()-_=+[{]}\\|;:'\",.<>?/";
        
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (specialCharacters.contains(String.valueOf(ch))) {
                hasSpecialChar = true;
            }
        }
        
        // Check if all required criteria are met
        return hasUppercase && hasDigit && hasSpecialChar;
    }
}
}
