package com.blaese.error.manager.entity;

import com.blaese.error.manager.util.enums.Environment;
import com.blaese.error.manager.util.enums.Level;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class Log implements Serializable {

    private static final long serialVersionUID = 5551220524872004788L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String details;

    @Column(nullable = false)
    private Environment environment;

    @Column(nullable = false)
    private Level level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "token", referencedColumnName = "id")
    private Token token;

}
