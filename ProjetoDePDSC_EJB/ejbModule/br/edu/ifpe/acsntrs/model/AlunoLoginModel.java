package br.edu.ifpe.acsntrs.model;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.edu.ifpe.acsntrs.entity.Aluno;

/**
 * Session Bean implementation class AlunoLoginBean
 * 
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@Stateless
@LocalBean
public class AlunoLoginModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8536310223566630768L;

	@PersistenceContext(unitName = "ProjetoDePDSC_PU")
	private EntityManager em;

	public AlunoLoginModel()
	{
	}

	public Aluno getAluno(String login, String senha)
	{
		Aluno aluno = em.createNamedQuery("Aluno.findByLogin", Aluno.class).setParameter("login", login).getSingleResult();
		if(aluno != null && senha.equals(aluno.getSenha()))
		{
			return aluno;
		}
		return null;
	}
}