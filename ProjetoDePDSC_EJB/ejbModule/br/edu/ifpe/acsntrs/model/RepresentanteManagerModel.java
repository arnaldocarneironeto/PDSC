package br.edu.ifpe.acsntrs.model;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.edu.ifpe.acsntrs.entity.Representante;

/**
 * Session Bean implementation class RepresentanteManagerModel
 * 
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@LocalBean
public class RepresentanteManagerModel implements RepresentanteManagerModelLocal
{
	@PersistenceContext(unitName = "ProjetoDePDSC_PU")
	private EntityManager em;

	@Resource
	SessionContext context;

	@EJB
	private EscolaManagerModel emm;

	/**
	 * Default constructor.
	 */
	public RepresentanteManagerModel()
	{
	}
	
	@Override
	public Representante save(Representante rep)
	{
		if(rep == null) return null;
		try
		{
			if(rep.getId() == null || read(rep) == null)
			{
				context.getUserTransaction().begin();
				em.persist(rep);
				context.getUserTransaction().commit();
			}
			else
			{
				context.getUserTransaction().begin();
				em.merge(rep);
				context.getUserTransaction().commit();
			}

			return read(rep);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Representante read(Representante rep)
	{
		Representante result = null;
		if(rep != null && rep.getId() != null)
		{
			result = em.find(Representante.class, rep.getId());
		}
		return result;
	}

	@Override
	public Representante read(String login, String senha)
	{
		Representante rep = em.createNamedQuery("Representante.findByLogin", Representante.class).setParameter("login", login).getSingleResult();
		if(rep != null && senha.equals(rep.getSenha()))
		{
			return rep;
		}
		return null;
	}

	@Override
	public List<Representante> read(String nome)
	{
		return em.createNamedQuery("Representante.findByNome", Representante.class).setParameter("nome", nome).getResultList();
	}
	
	@Override
	public List<Representante> read()
	{
		return em.createNamedQuery("Representante.findAll", Representante.class).getResultList();
	}

	@Override
	public void delete(Representante rep)
	{
		if(rep != null && rep.getId() != null)
		{
			try
			{
				context.getUserTransaction().begin();
				rep = em.getReference(Representante.class, rep.getId());
				em.remove(rep);
				context.getUserTransaction().commit();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}