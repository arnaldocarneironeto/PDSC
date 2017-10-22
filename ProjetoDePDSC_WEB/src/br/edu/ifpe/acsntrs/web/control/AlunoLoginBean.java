package br.edu.ifpe.acsntrs.web.control;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import br.edu.ifpe.acsntrs.entity.Aluno;
import br.edu.ifpe.acsntrs.entity.Escola;
import br.edu.ifpe.acsntrs.model.AlunoLoginModel;

/**
 *
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@ManagedBean(name = "AlunoLoginBean")
@SessionScoped
public class AlunoLoginBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7530370293109592342L;

	private String login;

	private String nome;

	private String senha;

	private Boolean logado;

	private Map<String, Float> notas;

	private List<Escola> preferencia;

	private Escola escola_que_selecionou_este_aluno;

	@EJB
	private AlunoLoginModel loginModel;

	public AlunoLoginBean()
	{
		this.sair();
	}

	public void logar()
	{
		Aluno aluno = loginModel.getAluno(this.login, this.senha);
		if(aluno != null)
		{
			this.nome = aluno.getNome();
			this.senha = "";
			this.notas = aluno.getNotas();
			this.preferencia = aluno.getPreferencia();
			this.escola_que_selecionou_este_aluno = aluno.getEscola_que_selecionou_este_aluno();
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
		this.notas = null;
		this.preferencia = null;
		this.escola_que_selecionou_este_aluno = null;
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

	public Map<String, Float> getNotas()
	{
		return notas;
	}

	public void setNotas(Map<String, Float> notas)
	{
		this.notas = notas;
	}

	public List<Escola> getPreferencia()
	{
		return preferencia;
	}

	public void setPreferencia(List<Escola> preferencia)
	{
		this.preferencia = preferencia;
	}

	public Escola getEscola_que_selecionou_este_aluno()
	{
		return escola_que_selecionou_este_aluno;
	}

	public void setEscola_que_selecionou_este_aluno(Escola escola_que_selecionou_este_aluno)
	{
		this.escola_que_selecionou_este_aluno = escola_que_selecionou_este_aluno;
	}
}