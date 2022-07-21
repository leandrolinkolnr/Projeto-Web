package com.progweb.DiarioEscolar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.progweb.DiarioEscolar.domain.Aluno;
import com.progweb.DiarioEscolar.domain.Professor;

import com.progweb.DiarioEscolar.services.AlunoService;
import com.progweb.DiarioEscolar.services.ProfessorService;

@SpringBootApplication
public class DiarioEscolarApplication implements CommandLineRunner{

	@Autowired
	private AlunoService alunoService;
	@Autowired
	private ProfessorService professorService; 
	@Autowired 
	private BCryptPasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(DiarioEscolarApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		
		Aluno aluno1 = new Aluno("leandro","171080483", "leandrol@gmail.com", encoder.encode("leandro"));
		Professor prof1 = new Professor("Fabio","132521749", "Computacao","fabioc@gmail.com",encoder.encode("fabio") );
		
		alunoService.adicionarAluno(aluno1);
		professorService.adicionarProfessor(prof1);
	}
}
