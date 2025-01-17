package com.mcommerce.microserviceexpedition.web.controller;

import com.mcommerce.microserviceexpedition.dao.ExpeditionDao;
import com.mcommerce.microserviceexpedition.model.Expedition;
import com.mcommerce.microserviceexpedition.web.exception.ExpeditionNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@Controller @AllArgsConstructor
@RequestMapping("/Expeditions") @Slf4j
public class ExpeditionController {
    private final ExpeditionDao expeditionDao;

    @GetMapping("/{id}")
    public ResponseEntity<Expedition> ExpeditionById(@PathVariable int id) {
        Optional<Expedition> expedition = expeditionDao.findById(id);

        if(!expedition.isPresent())
            throw new ExpeditionNotFoundException(String.format("Expedition with id: %s was not found", id));

        return new ResponseEntity<>(expedition.get(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Void> createExpedition(@Valid @RequestBody Expedition expedition) {
        Expedition createdExpedition = expeditionDao.save(expedition);

        if(createdExpedition == null)   return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(createdExpedition.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateExpedition(@Valid @RequestBody Expedition expedition, @PathVariable int id) {
        Optional<Expedition> savedExpedition = expeditionDao.findById(id);
        if(!savedExpedition.isPresent()) throw new ExpeditionNotFoundException(
                                                                    String.format("No Expedition with id %s", id));

        expedition.setId(id);
        // On ne doit pas être capable de modifier la commande d'une expédition
        if(expedition.getIdCommande() != savedExpedition.get().getIdCommande())
            expedition.setIdCommande(savedExpedition.get().getIdCommande());

        try {
            expeditionDao.save(expedition);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.accepted().build();
        }
    }

    @GetMapping("command/{idCommande}")
    public ResponseEntity<Expedition> expeditionByCommand(@PathVariable int idCommande) {

        Optional<Expedition> expedition = expeditionDao.findByIdCommande(idCommande);

        if(!expedition.isPresent())
            throw new ExpeditionNotFoundException(String.format("No expedition for command id: %s", idCommande));

        return new ResponseEntity<>(expedition.get(), HttpStatus.OK);
    }
}
