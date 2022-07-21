package com.progweb.DiarioEscolar.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.progweb.DiarioEscolar.domain.Professor;
import com.progweb.DiarioEscolar.domain.dtos.ProfessorDTO;
import com.progweb.DiarioEscolar.mappers.MapperProfessor;
import com.progweb.DiarioEscolar.services.ProfessorService;
import com.progweb.DiarioEscolar.services.exceptions.ExistingObjectSameNameException;

@RestController
@RequestMapping(value = "/professores")
@Api(value = "Professor")
public class ControllerProfessor {
	
	@Autowired
    private MapperProfessor professorMapper;

	@Autowired
	private ProfessorService service;

	@GetMapping()
	@ApiOperation(value = "Mostra todos os professores cadastrados.")
	public List<ProfessorDTO> getProfessores() {
        List<Professor> professor = service.getProfessores();
        return professor.stream().map(professorMapper::convertToProfessorDTO)
                        .collect(Collectors.toList());
    }
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Procura professor pelo ID.")
	public ResponseEntity<?> procuraProfessorID(@PathVariable Long id){
        return new ResponseEntity<>(professorMapper.convertToProfessorDTO(service.encontrarPorID(id)), HttpStatus.OK);

	}
	
	@PostMapping
	@ApiOperation(value = "Cadastra um Professor.")
	public ResponseEntity<ProfessorDTO> cadastrarProfessor(@RequestBody ProfessorDTO professorDTO) throws ExistingObjectSameNameException{
		
        Professor professor = professorMapper.convertFromProfessorDTO(professorDTO);
		Professor novoProfessor=  service.adicionarProfessor(professor);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(novoProfessor.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Atualiza um Professor.")
	public ResponseEntity<ProfessorDTO> atualizarProfessor(@PathVariable Long id, @RequestBody ProfessorDTO professorDTO){
		
		Professor professor = professorMapper.convertFromProfessorDTO(professorDTO);
        ProfessorDTO novoprofessorAtl =  professorMapper.convertToProfessorDTO(service.atualizarProfessor(id, professor));

		return ResponseEntity.ok().body(novoprofessorAtl);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deleta um Professor.")
	public ResponseEntity<?> deletarProfessor(@PathVariable Long id){
		
		service.deletarProfessor(id);
		return ResponseEntity.status(HttpStatus.OK).body("O professor foi excluido.");
	}
}
