package com.blaese.error.manager.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
public class Token implements Serializable {

    private static final long serialVersionUID = 2597402454274007866L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Token parent;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private Boolean active;



}
