package com.blaese.error.manager.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Token implements Serializable {

    private static final long serialVersionUID = 2597402454274007866L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent", referencedColumnName = "id")
    private Token parent;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private Boolean active;

}
