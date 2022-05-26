package com.progweb.controller;

import java.util.Optional;

import com.progweb.domain.Professor;
import com.progweb.repository.ProfessorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController                                              //vai ter as rotas para o front end acessar
@RequestMapping("/professores")
public class controllerProfesssor {

    @Autowired
    private ProfessorRepository ProfessorRepository; 

    @GetMapping
    public List<Professor> getProfessor() {
        return ProfessorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Professor> getProfessorById(@PathVariable Long id) {
        return ProfessorRepository.findById(id);
    }

    @PostMapping
    public Professor createProfessor(@RequestBody Professor Professor) {
        return ProfessorRepository.save(Professor);
    }

    @PutMapping("/{id}")
    public Professor updateCoffee(@PathVariable("id") Long id, @RequestBody Professor Professor) {
        return ProfessorRepository.save(Professor);
    }

    @DeleteMapping("/{id}")
    public void deleteCoffee(@PathVariable Long id) {
        ProfessorRepository.deleteById(id);
    }
}
