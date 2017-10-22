package br.edu.ifpe.acsntrs.model;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.edu.ifpe.acsntrs.entity.Administrador;

/**
 * Session Bean implementation class AdministradorLoginModel
 * 
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@Stateless
@LocalBean
public class AdministradorLoginModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3838472799855980066L;

	@PersistenceContext(unitName = "ProjetoDePDSC_PU")
	private EntityManager em;

	public AdministradorLoginModel()
	{
	}

	public Administrador getAdminstrador(String login, String senha)
	{
		Administrador admin = em.createNamedQuery("Administrador.findByLogin", Administrador.class).setParameter("login", login).getSingleResult();
		if(admin != null && senha.equals(admin.getSenha()))
		{
			return admin;
		}
		return null;
	}
}