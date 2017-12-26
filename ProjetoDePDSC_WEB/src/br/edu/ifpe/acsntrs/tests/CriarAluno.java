package br.edu.ifpe.acsntrs.tests;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.ejb.embeddable.EJBContainer;
import javax.enterprise.context.spi.Context;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.ifpe.acsntrs.entity.Aluno;
import br.edu.ifpe.acsntrs.model.AlunoManagerModel;

/**
 * 
 * @author Tássio
 *
 */
class CriarAluno {
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
	void testCreateAluno() {

		aluno.setNome("Aluno");
		aluno.setEmail("aaa@aaa.com");
		aluno.setLogin("Alu");
		aluno.setSenha("123");
		
		alunoServico.save(aluno);
		
		assertNotNull(aluno.getId());
	}

}
