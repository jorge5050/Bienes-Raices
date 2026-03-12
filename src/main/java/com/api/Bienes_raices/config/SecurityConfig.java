package com.api.Bienes_raices.config; // Asegúrate de que el package coincida con tu proyecto

import com.api.Bienes_raices.models.Usuario;

import com.api.Bienes_raices.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // Activa la seguridad web de Spring
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Encriptación obligatoria para contraseñas
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // desactivado para que el POST funcione sin tokens
                .authorizeHttpRequests(auth -> auth
                        // Cambiamos "/contacto" por "/contacto/**" para incluir "/contacto/enviar"
                        .requestMatchers("/", "/nosotros", "/anuncios", "/blog", "/contacto/**", "/propiedad/**").permitAll()
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico").permitAll()

                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login") // Tu vista personalizada
                        .defaultSuccessUrl("/admin/dashboard", true) // Redirección tras éxito
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/") // Al salir, vuelve al inicio
                        .permitAll()
                );

        return http.build();
    }

    /**
     * Crea un usuario inicial en la base de datos si no existe ninguno.
     */
    @Bean
    CommandLineRunner init(UsuarioRepository repo, PasswordEncoder encoder) {
        return args -> {
            String adminEmail = "admin@bienesraices.com";
            if (repo.findByEmail(adminEmail).isEmpty()) {
                Usuario admin = new Usuario();
                admin.setEmail(adminEmail);
                // Importante: La contraseña debe guardarse ENCRIPTADA
                admin.setPassword(encoder.encode("admin123"));
                admin.setRol("ROLE_ADMIN");
                repo.save(admin);

                System.out.println("--------------------------------------------------");
                System.out.println("USUARIO CREADO: " + adminEmail);
                System.out.println("PASSWORD: admin123");
                System.out.println("--------------------------------------------------");
            }
        };
    }
}