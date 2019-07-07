package com.mcommerce.microserviceexpedition.dao;

import com.mcommerce.microserviceexpedition.model.Expedition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpeditionDao extends JpaRepository<Expedition, Integer> {

    Optional<Expedition> findByIdCommande(int IdCommande);
}
