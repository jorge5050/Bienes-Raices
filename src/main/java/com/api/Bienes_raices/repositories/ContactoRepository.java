package com.api.Bienes_raices.repositories;

import com.api.Bienes_raices.models.ContactoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactoRepository extends JpaRepository<ContactoModel, Long> {
}
