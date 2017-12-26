package br.edu.ifpe.acsntrs.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.embeddable.EJBContainer;
import javax.enterprise.context.spi.Context;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.ifpe.acsntrs.entity.*;
import br.edu.ifpe.acsntrs.model.*;

/**
 * 
 * @author Tássio
 *
 */
class DefinicaoEscola {

	private static EJBContainer eJBContainer;
	private static Context ctx;
	
	Aluno aluno = new Aluno();
	AlunoManagerModel alunoServico = new AlunoManagerModel();
	
	Escola escola = new Escola();
	EscolaManagerModel escolaServico = new EscolaManagerModel();
	
	@BeforeAll
	static void setUpBeforeClass() {
		eJBContainer = EJBContainer.createEJBContainer();
		ctx = (Context) eJBContainer.getContext();

	}

	@AfterAll
	static void tearDownAfterClass(){
		eJBContainer.close();
	}

	@BeforeEach
	void setUp() throws Exception {
		alunoServico = (AlunoManagerModel) eJBContainer.getContext().lookup("java:global/classes/AlunoManagerModel");
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void testDefinicaoEscolaAluno() {
		
		aluno.setNome("Aluno");
		aluno.setEmail("aaa@aaa.com");
		aluno.setLogin("Alu");
		aluno.setSenha("123");
		List<Escola> preferencia = new ArrayList<>();
		aluno.setPreferencia(preferencia);
		
		alunoServico.save(aluno);
		
		escola.setNome("Escola");
		escola.setDescricao("Descricao");
		escola.setConceito("conceito");
		preferencia.add(escola);
		
		aluno.setPreferencia(preferencia);
		
		alunoServico.save(aluno);
		
		assertNotNull(aluno.getPreferencia());
		
	}

}
