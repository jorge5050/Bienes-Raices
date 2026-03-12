package com.api.Bienes_raices.repositories;

import com.api.Bienes_raices.models.Propiedad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropiedadRepository extends JpaRepository<Propiedad, Long> {
}
