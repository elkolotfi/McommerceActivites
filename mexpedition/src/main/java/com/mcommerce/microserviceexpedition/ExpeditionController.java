package com.mcommerce.microserviceexpedition;

import com.mcommerce.microserviceexpedition.dao.ExpeditionDao;
import com.mcommerce.microserviceexpedition.model.Expedition;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Controller @AllArgsConstructor
@RequestMapping("/Expeditions")
public class ExpeditionController {
    private final ExpeditionDao expeditionDao;

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
