package com.progweb.DiarioEscolar.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.progweb.DiarioEscolar.builder.aluno.AlunoBuilder;
import com.progweb.DiarioEscolar.domain.Aluno;
import com.progweb.DiarioEscolar.repositories.RepositoryAluno;
import com.progweb.DiarioEscolar.services.AlunoService;
import com.progweb.DiarioEscolar.services.exceptions.ExistingObjectSameNameException;
import com.progweb.DiarioEscolar.services.exceptions.ObjectNotFoundException;

public class AlunoServiceTest {
    private static final long VALID_ALUNO_ID = 1L;
    private static final long INVALID_ALUNO_ID = 10L;

    @Mock
    private RepositoryAluno alunoRepository;

    @InjectMocks
    private AlunoService alunoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    class CreateTests {
        @Test
        void whenNewValidAlunoInformedThenItShouldBeCreated() throws ExistingObjectSameNameException {
           
            Aluno expectedSavedAluno = AlunoBuilder.builder().build().toAluno();
            when(alunoRepository.save(expectedSavedAluno)).thenReturn(expectedSavedAluno);
            Aluno createdAluno = alunoService.adicionarAluno(expectedSavedAluno);

            assertThat(createdAluno.getId(), is(equalTo(expectedSavedAluno.getId())));
            assertThat(createdAluno.getNome(), is(equalTo(expectedSavedAluno.getNome())));
        }

        @Test
        void whenAlreadyRegisteredAlunoInformedThenAnExceptionShouldBeThrown() {
          
            Aluno expectedAluno = AlunoBuilder.builder().build().toAluno();
            Aluno duplicatedAluno = expectedAluno;

            when(alunoRepository.findByNome(expectedAluno.getNome()))
                    .thenReturn(Optional.of(duplicatedAluno));

            assertThrows(ExistingObjectSameNameException.class, () -> alunoService.adicionarAluno(expectedAluno));
        }
    }

    @Nested
    class UpdateTests {
        @Test
        void whenValidAlunoIsGivenThenReturnAnUpdatedAluno() throws ObjectNotFoundException {
         
            Aluno expectedFoundAluno = AlunoBuilder.builder().build().toAluno();
            Aluno expectedUpdatedAluno = AlunoBuilder.builder().nome("Grapes").build().toAluno();

            when(alunoRepository.findById(VALID_ALUNO_ID)).thenReturn(Optional.of(expectedFoundAluno));
            when(alunoRepository.save(expectedUpdatedAluno)).thenReturn(expectedUpdatedAluno);

            Aluno updatedAluno = alunoService.atualizarAluno(VALID_ALUNO_ID, expectedUpdatedAluno);
            assertThat(updatedAluno, is(equalTo(expectedUpdatedAluno)));
        }

        @Test
        void whenInvalidAlunoIsGivenThenThrowAnException() {
           
            Aluno expectedFoundAluno = AlunoBuilder.builder().build().toAluno();
            when(alunoRepository.findById(INVALID_ALUNO_ID)).thenReturn(Optional.empty());
            assertThrows(ObjectNotFoundException.class, () -> alunoService.atualizarAluno(INVALID_ALUNO_ID, expectedFoundAluno));
        }
    }

    @Nested
    class DeleteTests {
        @Test
        void whenExclusionIsCalledWithValidIdThenAAlunoShouldBeDeleted() throws ObjectNotFoundException {
          
            Aluno expectedDeletedAluno = AlunoBuilder.builder().build().toAluno();
            when(alunoRepository.findById(expectedDeletedAluno.getId()))
                    .thenReturn(Optional.of(expectedDeletedAluno));
            doNothing().when(alunoRepository).deleteById(expectedDeletedAluno.getId());
            alunoService.deletarAluno(expectedDeletedAluno.getId());

            verify(alunoRepository, times(1)).findById(expectedDeletedAluno.getId());
            verify(alunoRepository, times(1)).delete(expectedDeletedAluno);
        }
    }
}
