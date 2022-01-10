package nl.tudelft.sem.template.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public class NoEncoding implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String password = rawPassword.toString();
        return password.equals(encodedPassword);
    }
}
