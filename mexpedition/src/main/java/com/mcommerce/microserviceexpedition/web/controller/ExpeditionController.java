package com.mcommerce.microserviceexpedition.web.controller;

import com.mcommerce.microserviceexpedition.dao.ExpeditionDao;
import com.mcommerce.microserviceexpedition.model.Expedition;
import com.mcommerce.microserviceexpedition.web.exception.ExpeditionNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@Controller @AllArgsConstructor
@RequestMapping("/Expeditions")
public class ExpeditionController {
    private final ExpeditionDao expeditionDao;

    @GetMapping("/{id}")
    public ResponseEntity<Expedition> ExpeditionById(@PathVariable int id) {
        Optional<Expedition> expedition = expeditionDao.findById(id);

        if(!expedition.isPresent())
            throw new ExpeditionNotFoundException(String.format("Expedition with id: {} was not found", id));

        return new ResponseEntity<Expedition>(expedition.get(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Expedition> createExpedition(@Valid @RequestBody Expedition expedition) {
        Expedition createdExpedition = expeditionDao.save(expedition);

        if(createdExpedition == null)   return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(createdExpedition.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
