package com.api.Bienes_raices.controllers;

import com.api.Bienes_raices.models.ContactoModel;
import com.api.Bienes_raices.services.ContactoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class ContactoController {

    @Autowired
    private ContactoService contactoService;

    @GetMapping("/contacto")
    public String mostrarContacto() {
        return "contacto"; // Nombre de tu archivo HTML
    }

    @GetMapping("/admin/mensajes/listado") // Asegúrate de que la URL empiece con "/"
    public String mostrarListado(Model model) {
        // 1. Obtener los mensajes desde el servicio
        List<ContactoModel> listaMensajes = contactoService.listarTodos();

        // 2. Pasarlos al HTML con el nombre "mensajes"
        model.addAttribute("mensajes", listaMensajes);

        // 3. Retornar la ruta del archivo (templates/admin/mensajes/listado.html)
        return "admin/mensajes/listado";
    }



    @PostMapping("/contacto/enviar")
    public String recibirContacto(@ModelAttribute ContactoModel contacto, RedirectAttributes flash) {
        try {
            contactoService.guardarContacto(contacto);
            flash.addFlashAttribute("exito", "¡Gracias! Tu mensaje ha sido enviado correctamente.");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Hubo un problema al enviar tu mensaje.");
        }
        return "redirect:/contacto";
    }

}
