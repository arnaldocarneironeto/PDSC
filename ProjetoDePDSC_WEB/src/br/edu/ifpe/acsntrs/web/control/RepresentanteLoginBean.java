package br.edu.ifpe.acsntrs.web.control;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import br.edu.ifpe.acsntrs.entity.Escola;
import br.edu.ifpe.acsntrs.entity.Representante;
import br.edu.ifpe.acsntrs.model.RepresentanteLoginModel;

/**
 *
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@ManagedBean(name = "RepresentanteLoginBean")
@SessionScoped
public class RepresentanteLoginBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -421078600582864790L;

	private String login;

	private String nome;

	private String senha;

	private Boolean logado;

	private Escola escola;

	@EJB
	private RepresentanteLoginModel loginModel;

	public RepresentanteLoginBean()
	{
		this.sair();
	}

	public void logar()
	{
		Representante representante = loginModel.getRepresentante(this.login, this.senha);
		if(representante != null)
		{
			this.nome = representante.getNome();
			this.senha = "";
			this.escola = representante.getEscola();
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
		this.escola = null;
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

	public Escola getEscola()
	{
		return escola;
	}

	public void setEscola(Escola escola)
	{
		this.escola = escola;
	}
}