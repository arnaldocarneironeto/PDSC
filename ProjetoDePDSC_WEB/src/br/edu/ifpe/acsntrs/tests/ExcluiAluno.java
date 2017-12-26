package br.edu.ifpe.acsntrs.tests;
import static org.junit.jupiter.api.Assertions.*;

import javax.ejb.embeddable.EJBContainer;
import javax.enterprise.context.spi.Context;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

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
class ExcluiAluno {
	private static EJBContainer eJBContainer;
	private static Context ctx;
	
	Aluno aluno = new Aluno();
	AlunoManagerModel alunoServico = new AlunoManagerModel();
	
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
	void testExcluirAluno() {

		aluno.setNome("Aluno");
		aluno.setEmail("aaa@aaa.com");
		aluno.setLogin("Alu");
		aluno.setSenha("123");
		
		alunoServico.save(aluno);
		
		alunoServico.delete(aluno);
		
		assertNull(aluno.getId());
	}

}
