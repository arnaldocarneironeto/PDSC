package br.edu.ifpe.acsntrs.web.control;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import br.edu.ifpe.acsntrs.entity.Administrador;
import br.edu.ifpe.acsntrs.model.AdministradorLoginModel;

/**
 *
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@ManagedBean(name = "AdministradorLoginBean")
@SessionScoped
public class AdministradorLoginBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4925390125759227416L;

	private String login;

	private String nome;

	private String senha;

	private Boolean logado;

	@EJB
	private AdministradorLoginModel loginModel;

	public AdministradorLoginBean()
	{
		this.sair();
	}

	public void logar()
	{
		Administrador admin = loginModel.getAdminstrador(this.login, this.senha);
		if(admin != null)
		{
			this.nome = admin.getNome();
			this.senha = "";
			this.logado = true;
		}
		else
		{
			FacesContext context = FacesContext.getCurrentInstance();
			ResourceBundle bundle = ResourceBundle.getBundle("Messages", context.getViewRoot().getLocale());
			context.addMessage(null, new FacesMessage(bundle.getString("usuario_nao_existe")));
		}
	}

	public String sair()
	{
		this.logado = false;
		this.nome = "";
		this.login = "";
		this.senha = "";
		return "index?faces-redirect=true";
	}

	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public String getSenha()
	{
		return senha;
	}

	public void setSenha(String senha)
	{
		this.senha = senha;
	}

	public Boolean getLogado()
	{
		return logado;
	}

	public void setLogado(Boolean logado)
	{
		this.logado = logado;
	}
}