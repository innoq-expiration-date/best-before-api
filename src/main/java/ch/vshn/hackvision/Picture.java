package ch.vshn.hackvision;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Cacheable
public class Picture extends PanacheEntity {
    @Column public String hash;
}