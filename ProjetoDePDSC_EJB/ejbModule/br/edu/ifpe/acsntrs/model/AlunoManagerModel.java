package br.edu.ifpe.acsntrs.model;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import br.edu.ifpe.acsntrs.entity.Aluno;

/**
 * Session Bean implementation class AlunoManagerModel
 * 
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@LocalBean
public class AlunoManagerModel implements AlunoManagerModelLocal
{
	@PersistenceContext(unitName = "ProjetoDePDSC_PU")
	private EntityManager em;

	@Resource
	SessionContext context;

	public AlunoManagerModel()
	{
	}

	public Aluno save(Aluno aluno)
	{
		if(aluno == null) return null;
		try
		{
			if(aluno.getId() == null || read(aluno) == null)
			{
				context.getUserTransaction().begin();
				em.persist(aluno);
				context.getUserTransaction().commit();
			}
			else
			{
				context.getUserTransaction().begin();
				em.merge(aluno);
				context.getUserTransaction().commit();
			}

			return read(aluno);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Aluno read(Aluno aluno)
	{
		Aluno result = null;
		if(aluno != null && aluno.getId() != null)
		{
            return em.find(Aluno.class, aluno.getId());
		}
		return result;
	}

	@Override
	public Aluno read(String login, String senha)
	{
		Aluno aluno = em.createNamedQuery("Aluno.findByLogin", Aluno.class).setParameter("login", login).getSingleResult();
		if(aluno != null && senha.equals(aluno.getSenha()))
		{
			return aluno;
		}
		return null;
	}

	@Override
	public List<Aluno> read(String nome)
	{
		return em.createNamedQuery("Aluno.findByNome", Aluno.class).setParameter("nome", nome).getResultList();
	}
	
	@Override
	public List<Aluno> read()
	{
		return em.createNamedQuery("Aluno.findAll", Aluno.class).getResultList();
	}

	@Override
	public void delete(Aluno aluno)
	{
		if(aluno != null && aluno.getId() != null)
		{
			try
			{
				context.getUserTransaction().begin();
				aluno = em.getReference(Aluno.class, aluno.getId());
				em.remove(aluno);
				context.getUserTransaction().commit();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public boolean alreadyExists(String login, String email)
	{
		try
		{
			em.createNamedQuery("Aluno.findByLogin", Aluno.class).setParameter("login", login).getSingleResult();
			return true;
		}
		catch(NoResultException e) {}
		try
		{
			em.createNamedQuery("Aluno.findByEmail", Aluno.class).setParameter("email", email).getSingleResult();
			return true;
		}
		catch (NoResultException e) {}
		return false;
	}
}