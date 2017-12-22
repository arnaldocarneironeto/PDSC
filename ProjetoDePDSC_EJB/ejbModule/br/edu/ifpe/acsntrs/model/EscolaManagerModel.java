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
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import br.edu.ifpe.acsntrs.entity.Aluno;
import br.edu.ifpe.acsntrs.entity.Escola;

/**
 * Session Bean implementation class EscolaManagerModel
 * 
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
@LocalBean
public class EscolaManagerModel implements EscolaManagerModelLocal
{
	@PersistenceContext(unitName = "ProjetoDePDSC_PU")
	private EntityManager em;

	@Resource
	SessionContext context;

	/**
	 * Default constructor.
	 */
	public EscolaManagerModel()
	{
	}

	@Override
	public Escola save(Escola escola)
	{
		if(escola == null) return null;
		try
		{
			if(escola.getId() == null || read(escola) == null)
			{
				context.getUserTransaction().begin();
				em.persist(escola);
				context.getUserTransaction().commit();
			}
			else
			{
				context.getUserTransaction().begin();
				em.merge(escola);
				context.getUserTransaction().commit();
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
			try
			{
				context.getUserTransaction().begin();
				Escola e = em.find(Escola.class, escola.getId());
				e.getAlunos_selecionados().size();
				e.getCriterios().size();
				context.getUserTransaction().commit();
				return e;
			}
			catch(IllegalStateException | NotSupportedException | SystemException | SecurityException | RollbackException | HeuristicMixedException | HeuristicRollbackException e1)
			{
				e1.printStackTrace();
				return null;
			}
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
				context.getUserTransaction().begin();
				escola = em.getReference(Escola.class, escola.getId());
				List<Aluno> alunos_que_preferem_esta_escola = escola.getAlunos_que_preferem_esta_escola();
	            for (Aluno alunos_que_preferem_esta_escolaAluno :alunos_que_preferem_esta_escola)
	            {
	                alunos_que_preferem_esta_escolaAluno.getPreferencia().remove(escola);
	                em.merge(alunos_que_preferem_esta_escolaAluno);
	            }
	            List<Aluno> alunos_selecionados = escola.getAlunos_selecionados();
	            for (Aluno alunos_selecionadosAluno :alunos_selecionados)
	            {
	                alunos_selecionadosAluno.setEscola_que_selecionou_este_aluno(null);
	                em.merge(alunos_selecionadosAluno);
	            }
	            escola.getRepresentante().setEscola(null);
	            em.merge(escola.getRepresentante());
				em.remove(escola);
				context.getUserTransaction().commit();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}