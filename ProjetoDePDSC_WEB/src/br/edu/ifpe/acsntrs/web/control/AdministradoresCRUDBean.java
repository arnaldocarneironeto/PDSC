package br.edu.ifpe.acsntrs.web.control;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.edu.ifpe.acsntrs.entity.Administrador;
import br.edu.ifpe.acsntrs.model.AdministradorManagerModel;

/**
 * 
 * @author Tássio
 */
@ManagedBean(eager = true)
@SessionScoped
public class AdministradoresCRUDBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5080647357343992845L;
	
	private Boolean editando;
	
	private Administrador administradorAtual;
	
	@EJB
	private AdministradorManagerModel administradorManagerModel;

	public AdministradoresCRUDBean()
	{
		this.editando = false;
		this.administradorAtual = new Administrador();
	}
	
	public String listar()
	{
		return "crudaluno?faces-redirect=true";
	}
	
	public List<Administrador> lista()
	{
		return administradorManagerModel.read();
	}
	
	public void novo()
	{
		this.administradorAtual = new Administrador();
		this.editando = true;
	}
	
	public void salvar()
	{
		administradorManagerModel.save(administradorAtual);
		this.editando = false;
	}
	
	public void cancelar()
	{
		this.editando = false;
	}
	
	public void atualizar(Administrador administrador)
	{
		this.administradorAtual = administrador;
		this.editando = true;
	}
	
	public void apagar(Administrador administrador)
	{
		administradorManagerModel.delete(administrador);
	}

	public Boolean getEditando()
	{
		return editando;
	}

	public void setEditando(Boolean editando)
	{
		this.editando = editando;
	}

	public Administrador getAdministradorAtual()
	{
		return administradorAtual;
	}

	public void setAdministradorAtual(Administrador administradorAtual)
	{
		this.administradorAtual = administradorAtual;
	}
}