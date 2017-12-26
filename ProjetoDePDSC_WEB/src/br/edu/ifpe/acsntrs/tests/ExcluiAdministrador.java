package br.edu.ifpe.acsntrs.tests;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.ejb.embeddable.EJBContainer;
import javax.enterprise.context.spi.Context;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.ifpe.acsntrs.entity.Administrador;
import br.edu.ifpe.acsntrs.model.AdministradorManagerModel;

/**
 * 
 * @author Tássio
 *
 */
class ExcluiAdministrador {
	private static EJBContainer eJBContainer;
	private static Context ctx;
	
	Administrador administrador = new Administrador();
	AdministradorManagerModel administradorServico = new AdministradorManagerModel();
	
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
		administradorServico = (AdministradorManagerModel) eJBContainer.getContext().lookup("java:global/classes/AdministradorManagerModel");
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	
	@Test
	void testExcluirAdm() {

		administrador.setNome("Administrador");
		administrador.setEmail("aaa@aaa.com");
		administrador.setLogin("Adm");
		administrador.setSenha("123");
		
		administradorServico.save(administrador);
		
		administradorServico.delete(administrador); 
		
		assertNull(administrador.getId());
	}

}
