package com.progweb.DiarioEscolar.builder.aluno;

import com.progweb.DiarioEscolar.domain.dtos.AlunoDTO;

import lombok.Builder;

@Builder
public class AlunoDTOBuilder {
    
    @Builder.Default
    private String nome = "Leandro";

    @Builder.Default
    private String matricula = "171080483";

    @Builder.Default
    private String email = "leandrol@gmail.com";
   
    @Builder.Default
    private String senha = "1313";
    
    public AlunoDTO toAlunoDTO() {
        return new AlunoDTO(nome, matricula, email, senha);
    }
}
