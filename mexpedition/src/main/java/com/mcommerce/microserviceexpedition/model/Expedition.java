package com.mcommerce.microserviceexpedition.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity @Setter @Getter @NoArgsConstructor
public class Expedition {

    @Id
    @GeneratedValue
    private int id;
    private int idCommand;
    private int etat;
}
