package com.progweb.DiarioEscolar.settings;

import com.progweb.DiarioEscolar.mappers.MapperAluno;
import com.progweb.DiarioEscolar.mappers.MapperProfessor;
import com.progweb.DiarioEscolar.mappers.MapperTurma;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public MapperAluno alunoMapper() {
        return new MapperAluno();
    }

    @Bean
    public MapperProfessor professorMapper() {
        return new MapperProfessor();
    }

    @Bean
    public MapperTurma MapperTurma() {
        return new MapperTurma();
    }
}
