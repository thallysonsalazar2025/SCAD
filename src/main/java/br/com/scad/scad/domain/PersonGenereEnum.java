package br.com.scad.scad.domain;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum PersonGenereEnum {ACTION, TERROR, ROMANCE, SUSPENSE, ANIME;

    private  String name;

    PersonGenereEnum() {
        this.name = this.name().toLowerCase();
    }

}