package br.edu.ifpe.acsntrs.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

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
class CadastrarEscola {

	private static EJBContainer eJBContainer;
	private static Context ctx;
	
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
		escolaServico = (EscolaManagerModel) eJBContainer.getContext().lookup("java:global/classes/EscolaManagerModel");
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void testExcluirEscola() {
		escola.setNome("Escola");
		escola.setDescricao("Descricao");
		escola.setConceito("conceito");
		Map<String, Float> criterios = new HashMap<>();
		escola.setCriterios(criterios);		
		escolaServico.save(escola);
		criterios.put("Portugues", (float) 1);
		escola.setCriterios(criterios);
		
		escolaServico.save(escola);
		
		assertNotNull(escola.getId());
	}

}
