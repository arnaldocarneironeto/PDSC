package br.edu.ifpe.acsntrs.web.control;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import br.edu.ifpe.acsntrs.entity.Aluno;
import br.edu.ifpe.acsntrs.model.AlunoManagerModel;

@ManagedBean(eager = true)
@ApplicationScoped
public class AlunosCRUDBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5080647357343992845L;
	
	private Boolean editando;
	
	private Aluno alunoAtual;
	
	@EJB
	private AlunoManagerModel alunoManagerModel;

	public AlunosCRUDBean()
	{
		this.editando = false;
		this.alunoAtual = new Aluno();
	}
	
	public String listar()
	{
		return "crudaluno?faces-redirect=true";
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
		alunoManagerModel.save(alunoAtual);
		return cancelar();
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