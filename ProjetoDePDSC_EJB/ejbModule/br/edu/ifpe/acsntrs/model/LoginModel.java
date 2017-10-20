package br.edu.ifpe.acsntrs.model;

import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class LoginBean
 */
@Stateless
@LocalBean
public class LoginModel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1060469619624791018L;
	
	@PersistenceContext(unitName = "ProjetoDePDSC_PU")
	private EntityManager em;

	/**
     * Default constructor. 
     */
    public LoginModel() {
        // TODO Auto-generated constructor stub
    }

}
