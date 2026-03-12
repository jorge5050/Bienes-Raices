package com.api.Bienes_raices.services;
import com.api.Bienes_raices.models.Propiedad;
import java.util.List;

public interface PropiedadService {
    public List<Propiedad> listarTodas();
    public void guardar(Propiedad propiedad);
    public Propiedad buscarPorId(Long id);
    public void eliminar(Long id);
}
