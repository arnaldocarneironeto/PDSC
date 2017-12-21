package br.edu.ifpe.acsntrs.model;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.edu.ifpe.acsntrs.entity.Administrador;

/**
 * Session Bean implementation class AdministradorManagerModel
 * 
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@LocalBean
public class AdministradorManagerModel implements AdministradorManagerModelLocal
{
	@PersistenceContext(unitName = "ProjetoDePDSC_PU")
	private EntityManager em;

	@Resource
	SessionContext context;

	public AdministradorManagerModel()
	{
	}

	@Override
	public Administrador save(Administrador admin)
	{
		if(admin == null) return null;
		try
		{
			if(admin.getId() == null || read(admin) == null)
			{
				context.getUserTransaction().begin();
				em.persist(admin);
				context.getUserTransaction().commit();
			}
			else
			{
				context.getUserTransaction().begin();
				em.merge(admin);
				context.getUserTransaction().commit();
			}

			return read(admin);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Administrador read(Administrador admin)
	{
		Administrador result = null;
		if(admin != null && admin.getId() != null)
		{
			result = em.find(Administrador.class, admin.getId());
		}
		return result;
	}

	@Override
	public Administrador read(String login, String senha)
	{
		Administrador admin = em.createNamedQuery("Administrador.findByLogin", Administrador.class).setParameter("login", login).getSingleResult();
		if(admin != null && senha.equals(admin.getSenha()))
		{
			return admin;
		}
		return null;
	}

	@Override
	public List<Administrador> read(String nome)
	{
		return em.createNamedQuery("Administrador.findByNome", Administrador.class).setParameter("nome", nome).getResultList();
	}
	
	@Override
	public List<Administrador> read()
	{
		return em.createNamedQuery("Administrador.findAll", Administrador.class).getResultList();
	}

	@Override
	public void delete(Administrador admin)
	{
		if(admin != null && admin.getId() != null)
		{
			try
			{
				context.getUserTransaction().begin();
	            admin = em.getReference(Administrador.class, admin.getId());
				em.remove(admin);
				context.getUserTransaction().commit();				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}