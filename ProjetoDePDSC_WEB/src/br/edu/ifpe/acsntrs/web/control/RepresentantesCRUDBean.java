package br.edu.ifpe.acsntrs.web.control;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.edu.ifpe.acsntrs.entity.Representante;
import br.edu.ifpe.acsntrs.model.RepresentanteManagerModel;

/**
*
* @author Tássio
*/
@ManagedBean(eager = true)
@SessionScoped
public class RepresentantesCRUDBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5080647357343992845L;
	
	private Boolean editando;
	
	private Representante representanteAtual;
	
	@EJB
	private RepresentanteManagerModel representanteManagerModel;

	public RepresentantesCRUDBean()
	{
		this.editando = false;
		this.representanteAtual = new Representante();
	}
	
	public String listar()
	{
		return "crudrepresentante?faces-redirect=true";
	}
	
	public List<Representante> lista()
	{
		return representanteManagerModel.read();
	}
	
	public void novo()
	{
		this.representanteAtual = new Representante();
		this.editando = true;
	}
	
	public void salvar()
	{
		representanteManagerModel.save(representanteAtual);
		this.editando = false;
	}
	
	public void cancelar()
	{
		this.editando = false;
	}
	
	public void atualizar(Representante representante)
	{
		this.representanteAtual = representante;
		this.editando = true;
	}
	
	public void apagar(Representante representante)
	{
		representanteManagerModel.delete(representante);
	}

	public Boolean getEditando()
	{
		return editando;
	}

	public void setEditando(Boolean editando)
	{
		this.editando = editando;
	}

	public Representante getRepresentanteAtual()
	{
		return representanteAtual;
	}

	public void setRepresentanteAtual(Representante representanteAtual)
	{
		this.representanteAtual = representanteAtual;
	}
}