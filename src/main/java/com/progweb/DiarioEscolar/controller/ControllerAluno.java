package com.progweb.DiarioEscolar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import com.progweb.DiarioEscolar.domain.Aluno;
import com.progweb.DiarioEscolar.domain.dtos.AlunoDTO;
import com.progweb.DiarioEscolar.mappers.MapperAluno;
import com.progweb.DiarioEscolar.services.AlunoService;
import com.progweb.DiarioEscolar.services.exceptions.ExistingObjectSameNameException;

@RestController
@RequestMapping(value= "/alunos")
@Api(value = "Aluno")
public class ControllerAluno {
	
	@Autowired
    private MapperAluno alunoMapper;

	@Autowired
	private AlunoService service;

	@GetMapping()
	@ApiOperation(value = "Mostra todos os alunos cadastrados")
	public List<AlunoDTO> getAluno() {
        List<Aluno> alunos = service.getAluno();
        return alunos.stream().map(alunoMapper::convertToAlunoDTO)
                        .collect(Collectors.toList());
    }
	
	@GetMapping("/{id}")
	@ApiOperation(value = "Procura aluno pelo ID.")
	public ResponseEntity<?> procuraAlunoID(@PathVariable Long id){
        return new ResponseEntity<>(alunoMapper.convertToAlunoDTO(service.encontrarPorID(id)), HttpStatus.OK);

	}
	
	@PostMapping
	@ApiOperation(value = "Cadastra aluno.")
	public ResponseEntity<AlunoDTO> cadastraAluno(@RequestBody AlunoDTO alunoDTO) throws ExistingObjectSameNameException{
		
        Aluno aluno = alunoMapper.convertFromAlunoDTO(alunoDTO);
		Aluno novoAluno=  service.adicionarAluno(aluno);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(novoAluno.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "Atualiza aluno.")
	public ResponseEntity<AlunoDTO> atualizarAluno(@PathVariable Long id, @RequestBody AlunoDTO alunoDTO){
		Aluno aluno = alunoMapper.convertFromAlunoDTO(alunoDTO);
        AlunoDTO alunoAtualizado =  alunoMapper.convertToAlunoDTO(service.atualizarAluno(id, aluno));
		return ResponseEntity.ok().body(alunoAtualizado);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deletar aluno")
	public ResponseEntity<?> deletarAluno(@PathVariable Long id){
		service.deletarAluno(id);
		return ResponseEntity.status(HttpStatus.OK).body("Aluno excluido");
	}
}
