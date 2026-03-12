package com.api.Bienes_raices.services;

import com.api.Bienes_raices.models.ContactoModel;
import com.api.Bienes_raices.repositories.ContactoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service

public class ContactoServiceImp implements ContactoService {

    @Autowired
    private ContactoRepository contactoRepository;


    @Override
    public List<ContactoModel> listarTodos() {
        return contactoRepository.findAll();
    }

    @Override
    public void guardarContacto(ContactoModel contacto) {
         contactoRepository.save(contacto);

    }

    @Override
    public void eliminar(Long id) {
        contactoRepository.deleteById(id);
    }
}
