package br.edu.ifpe.acsntrs.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import br.edu.ifpe.acsntrs.entity.Administrador;
import br.edu.ifpe.acsntrs.entity.Escola;
import br.edu.ifpe.acsntrs.entity.Representante;
import br.edu.ifpe.acsntrs.entity.Usuario;

/**
 * Session Bean implementation class UsuarioLoginModel
 * 
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@Stateless
@LocalBean
public class UsuarioLoginModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7940464812308512472L;
	
	@PersistenceContext(unitName = "ProjetoDePDSC_PU")
	private EntityManager em;

	public UsuarioLoginModel()
	{
	}

	public Usuario getUsuario(String login, String senha)
	{
		try
		{
			Usuario usuario = em.createNamedQuery("Usuario.findByLogin", Usuario.class).setParameter("login", login).getSingleResult();
			if(usuario != null && senha.equals(usuario.getSenha()))
			{
				return usuario;
			}
			return null;
		}
		catch(NoResultException e)
		{
			return null;
		}
	}
	
	public Boolean adminExists()
	{
		try
		{
			int count = ((Number) em.createNamedQuery("Administrador.countAll").getSingleResult()).intValue();
			return count > 0;
		}
		catch(NoResultException e)
		{
			return false;
		}
	}

	public void createDefaultAdmin()
	{
		Administrador admin = new Administrador();
		admin.setNome("Administrador");
		admin.setLogin("admin");
		admin.setEmail("admin@sdvea");
		admin.setSenha("admin");
		em.persist(admin);
		
		Representante rep = new Representante();
		rep.setNome("Representante");
		rep.setLogin("rep");
		rep.setEmail("rep@rep");
		rep.setSenha("rep");
		em.persist(rep);
		
		Escola escola = new Escola();
		escola.setNome("Escola");
		escola.setConceito("A-");
		escola.setDescricao("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer vitae egestas massa. Pellentesque vehicula neque risus, non feugiat mi scelerisque a. Phasellus eros turpis, venenatis a commodo non, suscipit at odio. Etiam ornare turpis sit amet lectus ornare, sit amet facilisis ex aliquam. Mauris ex nunc, efficitur eu mattis in, egestas vel turpis. Duis blandit aliquet venenatis. Cras aliquam quis sem nec luctus.");
		escola.setVagas(30);
		escola.setAlunos_que_preferem_esta_escola(new ArrayList<>());
		escola.setAlunos_selecionados(new ArrayList<>());
		Map<String, Float> criterios = new HashMap<>();
		criterios.put("Matemática", 5f);
		criterios.put("Português", 4.5f);
		criterios.put("Religião", 0.5f);
		escola.setCriterios(criterios);
		
		escola.setRepresentante(rep);
		rep.setEscola(escola);
		em.persist(escola);
		em.merge(rep);
	}
}