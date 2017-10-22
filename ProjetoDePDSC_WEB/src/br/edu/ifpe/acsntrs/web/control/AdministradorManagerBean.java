package br.edu.ifpe.acsntrs.web.control;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import br.edu.ifpe.acsntrs.entity.Administrador;
import br.edu.ifpe.acsntrs.model.AdministradorManagerModel;

/**
 * 
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@ManagedBean(name = "AdministradorManagerBean", eager = true)
@ApplicationScoped
public class AdministradorManagerBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2121376999470526926L;

	private Boolean editando;

	private Administrador adminAtual;

	@EJB
	private AdministradorManagerModel adminManagerModel;

	public AdministradorManagerBean()
	{
		this.editando = false;
		this.adminAtual = null;
		this.adminManagerModel = new AdministradorManagerModel();
	}

	public String listar()
	{
		return "crud_admin?faces-redirect=true";
	}

	public void novo()
	{
		this.adminAtual = new Administrador();
		this.editando = true;
	}

	public void salvar()
	{
		adminManagerModel.save(this.adminAtual);
		this.editando = false;
	}

	public void cancelar()
	{
		this.editando = false;
	}

	public void atualizar(Administrador admin)
	{
		this.adminAtual = admin;
		this.editando = true;
	}

	public void apagar(Administrador admin)
	{
		adminManagerModel.delete(admin);
	}

	public Boolean getEditando()
	{
		return editando;
	}

	public void setEditando(Boolean editando)
	{
		this.editando = editando;
	}

	public Administrador getAdminAtual()
	{
		return adminAtual;
	}

	public void setAdminAtual(Administrador adminAtual)
	{
		this.adminAtual = adminAtual;
	}
}