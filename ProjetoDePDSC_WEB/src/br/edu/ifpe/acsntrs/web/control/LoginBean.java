package br.edu.ifpe.acsntrs.web.control;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.edu.ifpe.acsntrs.entity.Administrador;
import br.edu.ifpe.acsntrs.entity.Aluno;
import br.edu.ifpe.acsntrs.entity.Representante;
import br.edu.ifpe.acsntrs.entity.Usuario;
import br.edu.ifpe.acsntrs.model.UsuarioLoginModel;

/**
 * 
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6606699918036222393L;

	private Usuario usuario;

	private Aluno aluno;

	private Representante representante;

	private Administrador administrador;

	private String login;

	private String senha;
	
	private Boolean logado;

	@EJB
	private UsuarioLoginModel loginModel;
	
	@ManagedProperty("#{pageControlBean}")
	private PageControlBean pageControlBean;
	
	@ManagedProperty("#{alteraAluno}")
	private AlteraAluno alteraAluno;

	public LoginBean()
	{
	}
	
	@PostConstruct
	private void init()
	{
		pageControlBean.vaParaInicio();
	}
	
	public String logon()
	{
		this.usuario = null;
		this.aluno = null;
		this.representante = null;
		this.administrador = null;
		this.usuario = loginModel.getUsuario(login, senha);
		this.login = "";
		this.senha = "";
		if(usuario != null)
		{
			if(this.usuario.isAdmin())
			{
				this.administrador = (Administrador) usuario;
			}
			if(this.usuario.isAluno())
			{
				this.aluno = (Aluno) usuario;
				alteraAluno.setAluno(aluno);
			}
			if(this.usuario.isRepresentante())
			{
				this.representante = (Representante) usuario;
			}
		}
		else
		{
			FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null, new FacesMessage("Nome de usuário ou senha inválidos"));
		}
		return "index?faces-redirect=true";
	}

	public String logoff()
	{
		this.usuario = null;
		this.aluno = null;
		this.representante = null;
		this.administrador = null;
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		if(pageControlBean != null)
		{
			pageControlBean.vaParaInicio();
		}
		return "index?faces-redirect=true";
	}

	public Usuario getUsuario()
	{
		return usuario;
	}

	public void setUsuario(Usuario usuario)
	{
		this.usuario = usuario;
	}

	public Aluno getAluno()
	{
		return aluno;
	}

	public void setAluno(Aluno aluno)
	{
		this.aluno = aluno;
	}

	public Representante getRepresentante()
	{
		return representante;
	}

	public void setRepresentante(Representante representante)
	{
		this.representante = representante;
	}

	public Administrador getAdministrador()
	{
		return administrador;
	}

	public void setAdministrador(Administrador administrador)
	{
		this.administrador = administrador;
	}

	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public String getSenha()
	{
		return senha;
	}

	public void setSenha(String senha)
	{
		this.senha = senha;
	}

	public void setPageControlBean(PageControlBean pageControlBean)
	{
		this.pageControlBean = pageControlBean;
	}

	public Boolean getLogado()
	{
		return logado;
	}

	public void setLogado(Boolean logado)
	{
		this.logado = logado;
	}

	public AlteraAluno getAlteraAluno()
	{
		return alteraAluno;
	}

	public void setAlteraAluno(AlteraAluno alteraAluno)
	{
		this.alteraAluno = alteraAluno;
	}
}