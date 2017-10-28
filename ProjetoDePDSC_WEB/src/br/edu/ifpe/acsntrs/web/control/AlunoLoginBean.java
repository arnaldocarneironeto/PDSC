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
import br.edu.ifpe.acsntrs.model.AlunoManagerModel;

/**
 *
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@ManagedBean(name = "AlunoLoginBean", eager = true)
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
	
	private String email;

	private Boolean logado;
	
	private Aluno aluno;

	private Map<String, Float> notas;

	private List<Escola> preferencia;

	private Escola escola_que_selecionou_este_aluno;
	
	private Float nota1;
	
	private Float nota2;
	
	private Float nota3;
	
	private Float nota4;
	
	private Float nota5;
	
	

	@EJB
	private AlunoLoginModel loginModel;
	
	@EJB
	private AlunoManagerModel managerModel;

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
			this.senha = aluno.getSenha();
			this.email = aluno.getEmail();
			this.notas = aluno.getNotas();
			this.preferencia = aluno.getPreferencia();
			this.escola_que_selecionou_este_aluno = aluno.getEscola_que_selecionou_este_aluno();
			this.aluno = aluno;
			this.logado = true;
		}
		else
		{
			FacesContext context = FacesContext.getCurrentInstance();
			ResourceBundle bundle = ResourceBundle.getBundle("Messages", context.getViewRoot().getLocale());
			context.addMessage(null, new FacesMessage(bundle.getString("usuario_nao_existe")));
		}
	}

	public void cadastrarNotas(Float... nts) {
		notas.put("Portugues", nts[0]);
		notas.put("Matematica", nts[1]);
		notas.put("Historia", nts[2]);
		notas.put("Geografia", nts[3]);
		notas.put("Biologia", nts[4]);
		aluno.setNotas(notas);
		aluno = managerModel.save(aluno);
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
		this.aluno = null;
		return "index?faces-redirect=true";
	}
	
	
	public String cadastrar()
	{
		return "crudAluno?faces-redirect=true";
	}
	
	public void atualizar()
	{
		aluno = managerModel.save(aluno);
//		return "index?faces-redirect=true";
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

	public Aluno getAluno()
	{
		return aluno;
	}

	public void setAluno(Aluno aluno)
	{
		this.aluno = aluno;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
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