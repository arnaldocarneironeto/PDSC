package br.edu.ifpe.acsntrs.model;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.edu.ifpe.acsntrs.entity.Escola;
import br.edu.ifpe.acsntrs.jpa.EscolaJpaController;

/**
 * Session Bean implementation class EscolaManagerModel
 * 
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@Stateless
@LocalBean
public class EscolaManagerModel implements EscolaManagerModelLocal
{
	@PersistenceContext(unitName = "ProjetoDePDSC_PU")
	private EntityManager em;

	@Resource
	SessionContext context;

	private EscolaJpaController controller;

	/**
	 * Default constructor.
	 */
	public EscolaManagerModel()
	{
		this.controller = new EscolaJpaController(context.getUserTransaction(), em.getEntityManagerFactory());
	}

	@Override
	public Escola save(Escola escola)
	{
		if(escola == null) return null;
		try
		{
			if(escola.getId() == null || read(escola) == null)
			{
				controller.create(escola);
			}
			else
			{
				controller.edit(escola);
			}

			return read(escola);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Escola read(Escola escola)
	{
		if(escola != null && escola.getId() != null)
		{
			return controller.findEscola(escola.getId());
		}
		return null;
	}

	@Override
	public List<Escola> read(String nome)
	{
		return em.createNamedQuery("Escola.findByNome", Escola.class).setParameter("nome", nome).getResultList();
	}
	
	@Override
	public List<Escola> read()
	{
		return em.createNamedQuery("Escola.findAll", Escola.class).getResultList();
	}

	@Override
	public void delete(Escola escola)
	{
		if(escola != null && escola.getId() != null)
		{
			try
			{
				controller.destroy(escola.getId());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}