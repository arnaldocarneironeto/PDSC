package br.edu.ifpe.acsntrs.model;

import java.io.Serializable;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.edu.ifpe.acsntrs.entity.Representante;

/**
 * Session Bean implementation class RepresentanteLoginModel
 * 
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@Stateless
@LocalBean
public class RepresentanteLoginModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -451512363275287317L;

	@PersistenceContext(unitName = "ProjetoDePDSC_PU")
	private EntityManager em;

	public RepresentanteLoginModel()
	{
	}

	public Representante getRepresentante(String login, String senha)
	{
		Representante rep = em.createNamedQuery("Representante.findByLogin", Representante.class).setParameter("login", login).getSingleResult();
		if(rep != null && senha.equals(rep.getSenha()))
		{
			return rep;
		}
		return null;
	}
}