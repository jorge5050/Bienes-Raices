package com.api.Bienes_raices.services;

import com.api.Bienes_raices.models.Propiedad;
import com.api.Bienes_raices.repositories.PropiedadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PropiedadServiceImpl implements PropiedadService{

    @Autowired
    private PropiedadRepository propiedadRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Propiedad> listarTodas() {
        return (List<Propiedad>) propiedadRepository.findAll();
    }

    @Override
    @Transactional
    public void guardar(Propiedad propiedad) {
        propiedadRepository.save(propiedad);
    }

    @Override
    @Transactional(readOnly = true)
    public Propiedad buscarPorId(Long id) {
        return propiedadRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        propiedadRepository.deleteById(id);
    }
}
