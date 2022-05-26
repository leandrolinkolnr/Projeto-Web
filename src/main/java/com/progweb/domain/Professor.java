package com.progweb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "professores")
public class Professor {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "name")                 
    private String name;

    @Column(name = "formacao")                 
    private String formacao;

    @Column(name = "matricula" , unique = true)                 
    private String matricula;

    @Column(name = "email" , unique = true)                 
    private String email;

    
    public Professor(String name, String formacao, String matricula, String email) {
        this.name = name;
        this.formacao = formacao;
        this.matricula = matricula;
        this.email = email;
    }
    
}
