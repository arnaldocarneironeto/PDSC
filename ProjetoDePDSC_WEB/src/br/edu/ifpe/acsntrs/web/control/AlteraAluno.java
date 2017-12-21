package br.edu.ifpe.acsntrs.web.control;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.edu.ifpe.acsntrs.entity.Aluno;
import br.edu.ifpe.acsntrs.model.AlunoManagerModel;

/**
 * 
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@ManagedBean
@SessionScoped
public class AlteraAluno implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4457500300767152557L;
	
	private String senha;
	
	private String confSenha;
	
	private Aluno aluno;
	
	@EJB
	private AlunoManagerModel managerModel;
	
	public AlteraAluno()
	{
	}
	
	public String salvar(Aluno aluno)
	{
		if(senha != null && senha.equals(confSenha))
		{
			aluno.setSenha(senha);
			aluno.setNome(this.aluno.getNome());
			aluno.setEmail(this.aluno.getEmail());
//			aluno.setNotas(this.aluno.getNotas());
			managerModel.save(aluno);			
		}
		return "index?faces-redirect=true";
	}

	public Aluno getAluno()
	{
		return aluno;
	}

	public void setAluno(Aluno aluno)
	{
		this.aluno = aluno;
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