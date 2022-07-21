package com.progweb.DiarioEscolar.builder.aluno;

import com.progweb.DiarioEscolar.domain.Aluno;

import lombok.Builder;

@Builder
public class AlunoBuilder {

    @Builder.Default
    private Long id = 10L;

    @Builder.Default
    private String nome = "Leandro";

    @Builder.Default
    private String matricula = "171080483";

    @Builder.Default
    private String email = "leandrol@gmail.com";
   
    @Builder.Default
    private String senha = "1313";

    public Aluno toAluno() {
        return new Aluno(nome, matricula, email, senha);
    }
} 
