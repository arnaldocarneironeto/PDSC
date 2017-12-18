package br.edu.ifpe.acsntrs.web.control;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.edu.ifpe.acsntrs.entity.Aluno;
import br.edu.ifpe.acsntrs.model.AlunoManagerModel;

/**
 * 
 * @author Tássio
 */
@ManagedBean
@RequestScoped
public class CadastroAlunoBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4063132219894971232L;
	
	private String nome;
	
	private String email;
	
	private String login;
	
	private String senha;
	
	private String confSenha;
	
	@EJB
	private AlunoManagerModel alunoManagerModel;

	public CadastroAlunoBean()
	{
		// TODO Auto-generated constructor stub
	}
	
	public void cadastrar()
	{
		if(senha != null) senha = senha.trim(); else { erroSenha(); return; }
		if(confSenha != null) confSenha = confSenha.trim(); else { erroSenha(); return; }
		if(senha.equals(confSenha) == false) { erroSenha(); return; }
		
		// As senhas são não nulas e conferem
		
		if(alunoManagerModel.alreadyExists(login, email) == false)
		{
			Aluno aluno = new Aluno();
			aluno.setNome(nome);
			aluno.setEmail(email);
			aluno.setLogin(login);
			aluno.setSenha(senha);
			aluno = alunoManagerModel.save(aluno);
			nome = "";
			email = "";
			login = "";
			senha = "";
			confSenha = "";
			FacesContext facesContext = FacesContext.getCurrentInstance();
	        facesContext.addMessage(null, new FacesMessage("Usuário cadastrado com sucesso"));
		}
		else
		{
			FacesContext facesContext = FacesContext.getCurrentInstance();
	        facesContext.addMessage(null, new FacesMessage("Já existe aluno com este email ou login"));
		}
	}

	private void erroSenha()
	{
		FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.addMessage(null, new FacesMessage("Senha inválida ou senhas não conferem"));
	}

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
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

	public String getConfSenha()
	{
		return confSenha;
	}

	public void setConfSenha(String confSenha)
	{
		this.confSenha = confSenha;
	}
}