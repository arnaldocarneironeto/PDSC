package br.edu.ifpe.acsntrs.model;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.edu.ifpe.acsntrs.entity.Administrador;
import br.edu.ifpe.acsntrs.jpa.AdministradorJpaController;

/**
 * Session Bean implementation class AdministradorManagerModel
 * 
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@Stateless
@LocalBean
public class AdministradorManagerModel implements AdministradorManagerModelLocal
{
	@PersistenceContext(unitName = "ProjetoDePDSC_PU")
	private EntityManager em;

	@Resource
	SessionContext context;

	private AdministradorJpaController controller;

	public AdministradorManagerModel()
	{
		this.controller = new AdministradorJpaController(context.getUserTransaction(), em.getEntityManagerFactory());
	}

	@Override
	public Administrador save(Administrador admin)
	{
		if(admin == null) return null;
		try
		{
			if(admin.getId() == null || read(admin) == null)
			{
				controller.create(admin);
			}
			else
			{
				controller.edit(admin);
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
			result = controller.findAdministrador(admin.getId());
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
	public void delete(Administrador admin)
	{
		if(admin != null && admin.getId() != null)
		{
			try
			{
				controller.destroy(admin.getId());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}