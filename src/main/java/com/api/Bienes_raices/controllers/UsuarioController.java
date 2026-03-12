package com.api.Bienes_raices.controllers;

import com.api.Bienes_raices.models.Usuario;
import com.api.Bienes_raices.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin") // Protegido por nuestra configuración de seguridad
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/usuarios/crear")
    public String formularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "admin/usuarios/crear";
    }

    @PostMapping("/usuarios/crear")
    public String guardarUsuario(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            // Usamos addFlashAttribute para que el mensaje sobreviva al redirect
            redirectAttributes.addFlashAttribute("error", "Ese correo ya está registrado por otro administrador.");
            return "redirect:/admin/usuarios/crear"; // Redirige al GET de la misma página
        }

        // ... resto del código para guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRol("ROLE_ADMIN");
        usuarioRepository.save(usuario);

        redirectAttributes.addFlashAttribute("exito", "Usuario creado correctamente.");
        return "redirect:/admin/dashboard";
    }

//    @PostMapping("/usuarios/crear")
//    public String guardarUsuario(@ModelAttribute Usuario usuario) {
//        // Encriptamos la contraseña antes de guardar
//        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
//        // Asignamos el rol de administrador por defecto
//        usuario.setRol("ROLE_ADMIN");
//
//        usuarioRepository.save(usuario);
//        return "redirect:/admin/dashboard?registroExitoso";
//    }
}
