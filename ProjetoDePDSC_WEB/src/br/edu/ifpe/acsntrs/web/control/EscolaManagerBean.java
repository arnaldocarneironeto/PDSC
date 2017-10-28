package br.edu.ifpe.acsntrs.web.control;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import br.edu.ifpe.acsntrs.entity.Escola;
import br.edu.ifpe.acsntrs.model.EscolaManagerModel;

/**
 * 
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@ManagedBean(name = "EscolaManagerBean", eager = true)
@ApplicationScoped
public class EscolaManagerBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1222586200581185475L;

	private Boolean editando;

	private Escola escolaAtual;

	@EJB
	private EscolaManagerModel escolaManagerModel;

	public EscolaManagerBean()
	{
		this.editando = false;
		this.escolaAtual = null;
		this.escolaManagerModel = new EscolaManagerModel();
	}

	public String listar()
	{
		return "crud_escola?faces-redirect=true";
	}
	
	public List<Escola> lista()
	{
		return escolaManagerModel.read();
	}

	public void novo()
	{
		this.escolaAtual = new Escola();
		this.editando = true;
	}

	public void salvar()
	{
		escolaManagerModel.save(this.escolaAtual);
		this.editando = false;
	}

	public void cancelar()
	{
		this.editando = false;
	}

	public void atualizar(Escola escola)
	{
		this.escolaAtual = escola;
		this.editando = true;
	}

	public void apagar(Escola escola)
	{
		escolaManagerModel.delete(escola);
	}

	public Boolean getEditando()
	{
		return editando;
	}

	public void setEditando(Boolean editando)
	{
		this.editando = editando;
	}

	public Escola getEscolaAtual()
	{
		return escolaAtual;
	}

	public void setEscolaAtual(Escola escolaAtual)
	{
		this.escolaAtual = escolaAtual;
	}
}