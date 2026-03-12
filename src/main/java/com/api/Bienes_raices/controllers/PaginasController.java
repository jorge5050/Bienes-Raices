package com.api.Bienes_raices.controllers;
import com.api.Bienes_raices.models.Propiedad;
import com.api.Bienes_raices.repositories.PropiedadRepository;
import com.api.Bienes_raices.services.PropiedadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Controller
public class PaginasController {

    @Autowired
    private PropiedadService propiedadService;
    private final PropiedadRepository propiedadRepository;

    // Inyectamos el repositorio para poder consultar la DB
    public PaginasController(PropiedadRepository propiedadRepository) {
        this.propiedadRepository = propiedadRepository;
    }

    @GetMapping("/propiedad/{id}")
    public String verPropiedad(@PathVariable Long id, Model model) {
        // Buscamos la propiedad por ID. Si no existe, puedes redirigir o mostrar error.
        Propiedad propiedad = propiedadService.buscarPorId(id);

        if(propiedad == null) {
            return "redirect:/anuncios";
        }

        model.addAttribute("propiedad", propiedad);
        return "propiedad"; // Nombre del archivo HTML
    }

    @GetMapping("/anuncios")
    public String anuncios(Model model) {
        // 1. Obtenemos todas las propiedades de la base de datos
        List<Propiedad> listaPropiedades = propiedadRepository.findAll();

        // 2. Las pasamos a la vista con el nombre "propiedades"
        model.addAttribute("propiedades", listaPropiedades);

        return "anuncios";
    }

    @GetMapping("/")
    public String inicio() {
        return "index"; // página principal
    }



    @GetMapping("/nosotros")
    public String nosotros() {
        return "nosotros";
    }

    

    @GetMapping("/blog")
    public String blog() {
        return "blog";
    }



    @GetMapping("/anuncio")
    public String anuncio(){
        return "anuncio";
    }

    @GetMapping("/entrada")
    public String entrada() {
        return "entrada";
    }

}
