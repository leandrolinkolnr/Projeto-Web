package com.progweb.DiarioEscolar.domain.dtos;

import java.util.List;

import com.progweb.DiarioEscolar.domain.Projeto;
import com.progweb.DiarioEscolar.domain.Turma;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AlunoDTO {
    
    public AlunoDTO(String nome, String matricula, String email, String senha) {
    }

    private Long id;
    private String nome;
    private String matricula;
    private String email;
    private String senha;
    private Projeto projeto;
    private String papelnoProjeto;
    private List<Turma> turmas;
}