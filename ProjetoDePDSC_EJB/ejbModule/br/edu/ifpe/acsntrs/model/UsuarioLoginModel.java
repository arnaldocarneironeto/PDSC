package br.edu.ifpe.acsntrs.model;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

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
}