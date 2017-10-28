package br.edu.ifpe.acsntrs.web.control;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import br.edu.ifpe.acsntrs.entity.Aluno;
import br.edu.ifpe.acsntrs.model.AlunoManagerModel;

/**
 * 
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@ManagedBean(name = "AlunoManagerBean", eager = true)
@ApplicationScoped
public class AlunoManagerBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4229715974038721072L;

	private Boolean editando;

	private Aluno alunoAtual;

	@EJB
	private AlunoManagerModel alunoManagerModel;

	public AlunoManagerBean()
	{
		this.editando = false;
		this.alunoAtual = new Aluno();
	}

	public String listar()
	{
		return "crud_aluno?faces-redirect=true";
	}
	
	public List<Aluno> lista()
	{
		return alunoManagerModel.read();
	}
	

	public void novo()
	{
		this.alunoAtual = new Aluno();
		this.editando = true;
	}

	public String salvar()
	{
		alunoManagerModel.save(this.alunoAtual);
		this.editando = false;
		return "index?faces-redirect=true";
	}

	public String cancelar()
	{
		this.editando = false;
		return "index?faces-redirect=true";
	}

	public void atualizar(Aluno aluno)
	{
		this.alunoAtual = aluno;
		this.editando = true;
	}

	public void apagar(Aluno aluno)
	{
		alunoManagerModel.delete(aluno);
	}

	public Boolean getEditando()
	{
		return editando;
	}

	public void setEditando(Boolean editando)
	{
		this.editando = editando;
	}

	public Aluno getAlunoAtual()
	{
		return alunoAtual;
	}

	public void setAlunoAtual(Aluno alunoAtual)
	{
		this.alunoAtual = alunoAtual;
	}
}