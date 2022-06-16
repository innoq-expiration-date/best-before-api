package ch.vshn.hackvision.picture;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
@Cacheable
public class Picture extends PanacheEntity {
    @Column public String hash;

    @Lob Byte[] image;
}
