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
@Table(name = "Turmas")
public class Turma {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")                 
    private String name;

    @Column(name = "sala")                 
    private String sala;

    
    @Column(name = "professor") 
    private Professor professor;

    
    public Turma(String name, String sala) {
        this.name = name;
       this.sala = sala;
    }
    
}
