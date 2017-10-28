package br.edu.ifpe.acsntrs.web.control;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import br.edu.ifpe.acsntrs.entity.Representante;
import br.edu.ifpe.acsntrs.model.RepresentanteManagerModel;

/**
 * 
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@ManagedBean(name = "RepresentanteManagerBean", eager = true)
@ApplicationScoped
public class RepresentanteManagerBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1781810682891231480L;

	private Boolean editando;

	private Representante repAtual;

	@EJB
	private RepresentanteManagerModel repManagerModel;

	public RepresentanteManagerBean()
	{
		this.editando = false;
		this.repAtual = null;
		this.repManagerModel = new RepresentanteManagerModel();
	}

	public String listar()
	{
		return "crud_representante?faces-redirect=true";
	}
	
	public List<Representante> lista()
	{
		return repManagerModel.read();
	}

	public void novo()
	{
		this.repAtual = new Representante();
		this.editando = true;
	}

	public void salvar()
	{
		repManagerModel.save(repAtual);
		this.editando = false;
	}

	public void cancelar()
	{
		this.editando = false;
	}

	public void atualizar(Representante rep)
	{
		this.repAtual = rep;
		this.editando = true;
	}

	public void apagar(Representante rep)
	{
		repManagerModel.delete(rep);
	}

	public Boolean getEditando()
	{
		return editando;
	}

	public void setEditando(Boolean editando)
	{
		this.editando = editando;
	}

	public Representante getRepAtual()
	{
		return repAtual;
	}

	public void setRepAtual(Representante repAtual)
	{
		this.repAtual = repAtual;
	}
}