package com.progweb.DiarioEscolar.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.progweb.DiarioEscolar.domain.Aluno;
import com.progweb.DiarioEscolar.domain.Professor;
import com.progweb.DiarioEscolar.domain.Projeto;
import com.progweb.DiarioEscolar.domain.dtos.PapelAlunoDTO;
import com.progweb.DiarioEscolar.repositories.RepositoryProjeto;
import com.progweb.DiarioEscolar.services.exceptions.ExistingObjectSameNameException;
import com.progweb.DiarioEscolar.services.exceptions.ObjectNotFoundException;

@Service
public class ProjetoService {
	
	@Autowired
	private RepositoryProjeto repository;
	@Autowired
	private AlunoService alunoService;
	@Autowired
	private ProfessorService professorService;
	
	public Projeto encontrarPorID(Long id){
		Optional<Projeto> obj = repository.findById(id);
		System.out.println(obj);
        return obj.orElseThrow(()-> new ObjectNotFoundException("Erro"));
    }

	public List<Projeto> getProjetos(){
		return repository.findAll();
	}

	public Projeto adicionarProjeto(Projeto projeto) throws ExistingObjectSameNameException{
		return repository.save(projeto);
	}

	public Projeto atualizarProjeto(Long id, Projeto projeto){
		projeto.setId(id);
        return repository.save(projeto);
	}

	public void deletarProjeto(Long id){
		Projeto projeto = encontrarPorID(id);
		repository.deleteById(projeto.getId());
	}

	public Projeto vincularProfessor(Long idProjeto, Long idProf) throws ExistingObjectSameNameException{

        Professor professor = professorService.encontrarPorID(idProf); 
		Projeto projeto= this.encontrarPorID(idProjeto);
		projeto.setProfessor(professor);
		professor.setProjeto(projeto);

        professorService.atualizarProfessor(idProf, professor);
        return  adicionarProjeto(projeto);
    }

	public Projeto adicionarAluno(Long idProjeto, Long idAluno, PapelAlunoDTO papelAlunoDTO) throws ExistingObjectSameNameException{

        Aluno aluno = alunoService.encontrarPorID(idAluno);
		aluno.setPapelProjeto(papelAlunoDTO.getPapelAluno());

		Projeto projeto = this.encontrarPorID(idProjeto);
		if(projeto.getProfessor()==null){
			throw new ObjectNotFoundException("Erro");
		}

		List<Aluno> novaLista = projeto.getAlunos();
		novaLista.add(aluno);

		projeto.setAlunos(novaLista);
		aluno.setProjeto(projeto);
		
		alunoService.atualizarAluno(idAluno, aluno);
		return adicionarProjeto(projeto);
    }
}
