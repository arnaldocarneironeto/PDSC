package br.edu.ifpe.acsntrs.model;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import br.edu.ifpe.acsntrs.entity.Aluno;
import br.edu.ifpe.acsntrs.jpa.AlunoJpaController;

/**
 * Session Bean implementation class AlunoManagerModel
 * 
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@Stateless
@LocalBean
public class AlunoManagerModel implements AlunoManagerModelLocal
{
	@PersistenceContext(unitName = "ProjetoDePDSC_PU")
	private EntityManager em;

	@Resource
	SessionContext context;

	private AlunoJpaController controller;

	public AlunoManagerModel()
	{
		this.controller = new AlunoJpaController(context.getUserTransaction(), em.getEntityManagerFactory());
	}

	public Aluno save(Aluno aluno)
	{
		if(aluno == null) return null;
		try
		{
			if(aluno.getId() == null || read(aluno) == null)
			{
				controller.create(aluno);
			}
			else
			{
				controller.edit(aluno);
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
			result = controller.findAluno(aluno.getId());
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
	public void delete(Aluno aluno)
	{
		if(aluno != null && aluno.getId() != null)
		{
			try
			{
				controller.destroy(aluno.getId());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}