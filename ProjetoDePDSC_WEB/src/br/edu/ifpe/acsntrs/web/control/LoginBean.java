package br.edu.ifpe.acsntrs.web.control;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;

import br.edu.ifpe.acsntrs.model.LoginModel;

@SessionScoped
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2963082372516744575L;
	
	@EJB
	private LoginModel loginModel;

	public LoginBean() {
		// TODO Auto-generated constructor stub
	}

}
