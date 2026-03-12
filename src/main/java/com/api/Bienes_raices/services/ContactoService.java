package com.api.Bienes_raices.services;

import com.api.Bienes_raices.models.ContactoModel;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public interface ContactoService {
    List<ContactoModel> listarTodos();
    void guardarContacto(ContactoModel contacto);
    void eliminar(Long id);
}
