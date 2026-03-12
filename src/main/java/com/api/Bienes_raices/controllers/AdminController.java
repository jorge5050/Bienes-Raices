package com.api.Bienes_raices.controllers;

import com.api.Bienes_raices.models.Propiedad;
import com.api.Bienes_raices.services.ContactoService;
import com.api.Bienes_raices.services.PropiedadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/admin") // PREFIJO GLOBAL: Todas las rutas empiezan con /admin
public class AdminController {

    @Autowired
    private PropiedadService propiedadService;

    @Autowired
    private ContactoService contactoService; // Para los mensajes

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        System.out.println("Entrando al Dashboard...");
        // Cargamos ambas listas para que el dashboard tenga toda la info
        model.addAttribute("propiedades", propiedadService.listarTodas());
        model.addAttribute("mensajes", contactoService.listarTodos());
        return "admin/dashboard";
    }


    @PostMapping("/mensajes/eliminar") // QUITAMOS el /admin de aquí
    public String eliminarMensaje(@RequestParam("id") Long id, RedirectAttributes flash) {
        try {
            contactoService.eliminar(id);
            flash.addFlashAttribute("exito", "Mensaje eliminado.");
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al eliminar.");
        }
        return "redirect:/admin/dashboard";
    }

    // Ruta final: /admin/propiedades/crear
    @GetMapping("/propiedades/crear")
    public String formularioCrear(Model model) {
        // Esto es lo que le falta a Thymeleaf para reconocer los campos
        model.addAttribute("propiedad", new Propiedad());
        return "admin/crear";
    }


    // 3. GUARDAR: Procesar el formulario de creación
    // Ruta final: /admin/propiedades/guardar
    @PostMapping("/propiedades/guardar")
    public String guardarPropiedad(@ModelAttribute Propiedad propiedad,
                                   @RequestParam("archivoImagen") MultipartFile imagen,
                                   RedirectAttributes mensaje) {
        try {
            if (!imagen.isEmpty()) {
                String nombreImagen = guardarArchivo(imagen);
                propiedad.setImagen(nombreImagen);
            }
            propiedadService.guardar(propiedad);
            mensaje.addFlashAttribute("exito", "Propiedad guardada con éxito.");
        } catch (IOException e) {
            mensaje.addFlashAttribute("error", "Error al subir la imagen.");
        }
        return "redirect:/admin/dashboard";
    }

    // 4. EDITAR: Mostrar formulario con datos cargados
    // Ruta final: /admin/propiedades/editar/{id}
    @GetMapping("/propiedades/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Propiedad propiedad = propiedadService.buscarPorId(id);
        model.addAttribute("propiedad", propiedad);
        return "admin/editar";
    }

    // 5. ACTUALIZAR: Procesar los cambios de la edición
    // Ruta final: /admin/propiedades/actualizar
    @PostMapping("/propiedades/actualizar")
    public String actualizar(@ModelAttribute Propiedad propiedad,
                             @RequestParam("archivoImagen") MultipartFile imagen,
                             RedirectAttributes mensaje) {
        try {
            // 1. Buscamos cómo está la propiedad actualmente en la base de datos
            Propiedad propiedadExistente = propiedadService.buscarPorId(propiedad.getId());

            // 2. ¿El usuario seleccionó un archivo nuevo?
            if (imagen != null && !imagen.isEmpty()) {

                // --- VALIDACIÓN DE TIPO (Imagen solamente) ---
                String contentType = imagen.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    mensaje.addFlashAttribute("error", "El archivo debe ser una imagen válida.");
                    return "redirect:/admin/propiedades/editar/" + propiedad.getId();
                }

                // --- AQUÍ VA EL BLOQUE QUE PREGUNTASTE: BORRADO FÍSICO ---
                // Si tiene una imagen previa, la borramos del disco antes de guardar la nueva
                String imagenVieja = propiedadExistente.getImagen();
                if (imagenVieja != null && !imagenVieja.isEmpty()) {
                    Path rutaVieja = Paths.get("img/uploads").resolve(imagenVieja).toAbsolutePath();
                    Files.deleteIfExists(rutaVieja); // Borra el archivo físico
                }

                // GUARDAR IMAGEN NUEVA
                String nombreImagen = guardarArchivo(imagen);
                propiedad.setImagen(nombreImagen);

            } else {
                // 3. Si NO seleccionó archivo, mantenemos la imagen que ya tenía
                propiedad.setImagen(propiedadExistente.getImagen());
            }

            // 4. Guardar cambios en la base de datos
            propiedadService.guardar(propiedad);
            mensaje.addFlashAttribute("exito", "Propiedad actualizada con éxito.");

        } catch (IOException e) {
            mensaje.addFlashAttribute("error", "Error al procesar la imagen: " + e.getMessage());
            return "redirect:/admin/propiedades/editar/" + propiedad.getId();
        }

        return "redirect:/admin/dashboard";
    }


    // 6. ELIMINAR: Borrar registro
    //    // Ruta final: /admin/propiedades/eliminar
    @PostMapping("/propiedades/eliminar")
    public String eliminar(@RequestParam("id") Long id, RedirectAttributes mensaje) {
        try {
            // 1. Buscamos la propiedad para saber qué imagen tiene
            Propiedad propiedad = propiedadService.buscarPorId(id);

            if (propiedad != null) {
                // 2. Intentamos borrar el archivo físico
                String nombreImagen = propiedad.getImagen();
                Path rutaImagen = Paths.get("img/uploads").resolve(nombreImagen).toAbsolutePath();

                try {
                    Files.deleteIfExists(rutaImagen);
                } catch (IOException e) {
                    // Si no se puede borrar el archivo (porque está abierto o permisos),
                    // solo mandamos un aviso a la consola pero seguimos con el borrado en BD
                    System.out.println("No se pudo borrar el archivo físico: " + nombreImagen);
                }

                // 3. Borramos el registro de la base de datos
                propiedadService.eliminar(id);
                mensaje.addFlashAttribute("exito", "Propiedad y su imagen fueron eliminadas correctamente.");
            }
        } catch (Exception e) {
            mensaje.addFlashAttribute("error", "Error al intentar eliminar la propiedad.");
        }

        return "redirect:/admin/dashboard";
    }


    private String guardarArchivo(MultipartFile archivo) throws IOException {
        // 1. Generar un nombre único para evitar que fotos con el mismo nombre se sobreescriban
        String nombreArchivo = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename();

        // 2. Definir la ruta relativa (asegúrate de que la carpeta 'img/uploads' exista)
        Path rutaCarpeta = Paths.get("img/uploads");

        // 3. Si la carpeta no existe, la creamos
        if (!Files.exists(rutaCarpeta)) {
            Files.createDirectories(rutaCarpeta);
        }

        // 4. Resolvemos la ruta completa del archivo
        Path rutaCompleta = rutaCarpeta.resolve(nombreArchivo).toAbsolutePath();

        // 5. Copiamos el archivo al destino
        Files.copy(archivo.getInputStream(), rutaCompleta);

        return nombreArchivo; // Retornamos el nombre para guardarlo en la Base de Datos
    }


}

