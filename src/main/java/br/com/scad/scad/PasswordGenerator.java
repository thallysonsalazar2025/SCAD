package br.com.scad.scad;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "senha-secreta-123";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        System.out.println("Senha criptografada para '" + rawPassword + "':");
        System.out.println(encodedPassword);
    }
}
