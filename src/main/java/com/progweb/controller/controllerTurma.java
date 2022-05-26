package com.progweb.controller;

import java.util.Optional;

import com.progweb.domain.Aluno;
import com.progweb.domain.Turma;
import com.progweb.repository.TurmaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController                                              //vai ter as rotas para o front end acessar
@RequestMapping("/Turmas")
public class controllerTurma {

    @Autowired
    private TurmaRepository TurmaRepository; 

    List<Aluno> listaAlunos = new ArrayList<>();

    @GetMapping
    public List<Turma> getTurma() {
        return TurmaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Turma> getTurmaById(@PathVariable Long id) {
        return TurmaRepository.findById(id);
    }

    @PostMapping
    public Turma createTurma(@RequestBody Turma Turma) {
        return TurmaRepository.save(Turma);
    }

    @PutMapping("/{id}")
    public Turma updateCoffee(@PathVariable("id") Long id, @RequestBody Turma Turma) {
        return TurmaRepository.save(Turma);
    }

    @DeleteMapping("/{id}")
    public void deleteCoffee(@PathVariable Long id) {
        TurmaRepository.deleteById(id);
    }

    @PatchMapping
    public void matricularAluno(Aluno aluno){
        listaAlunos.add(aluno);
    }

}
