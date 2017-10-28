package br.edu.ifpe.acsntrs.web.control;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
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
	
	private Float nota1;
	
	private Float nota2;
	
	private Float nota3;
	
	private Float nota4;
	
	private Float nota5;


	@EJB
	private RepresentanteLoginModel loginModel;
//	
//	@EJB
//	private EscolaManagerBean managerBean;

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
	
	public void salvar()
	{
		Map criterios = new HashMap<>();
		criterios.put("Portugues", nota1);
		criterios.put("Matematica", nota2);
		criterios.put("Historia", nota3);
		criterios.put("Geografia", nota4);
		criterios.put("Biologia", nota5);
		escola.setCriterios(criterios);
//		managerBean.setEscolaAtual(escola);
//		managerBean.salvar();
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
	
	public Float getNota1()
	{
		return nota1;
	}
	
	public void setNota1(Float nota1)
	{
		this.nota1 = nota1;
	}
	
	public Float getNota2()
	{
		return nota2;
	}
	
	public void setNota2(Float nota2)
	{
		this.nota2 = nota2;
	}
	
	public Float getNota3()
	{
		return nota3;
	}
	
	public void setNota3(Float nota3)
	{
		this.nota3 = nota3;
	}
	
	public Float getNota4()
	{
		return nota4;
	}
	
	public void setNota4(Float nota4)
	{
		this.nota4 = nota4;
	}
	
	public Float getNota5()
	{
		return nota5;
	}
	
	public void setNota5(Float nota5)
	{
		this.nota5 = nota5;
	}
}