package br.edu.ifpe.acsntrs.web.control;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * 
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@ManagedBean
@SessionScoped
public class PageControlBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 601666043117479842L;
	
	private int page;
	
	public PageControlBean()
	{
	}
	
	public String vaParaInicio()
	{
		this.page = 0;
		return "index?faces-redirect=true";
	}
	
	public String vaParaAlteracaoCadastral()
	{
		this.page = 1;
		return "index?faces-redirect=true";
	}
	
	public String vaParaPreferenciaEscolas()
	{
		this.page = 2;
		return "index?faces-redirect=true";
	}
	
	public String vaParaVerEscolaQueFoiAceito()
	{
		this.page = 3;
		return "index?faces-redirect=true";
	}
	
	public String vaParaGerenciarEscola()
	{
		this.page = 4;
		return "index?faces-redirect=true";
	}

	public String vaParaVerAlunos()
	{
		this.page = 5;
		return "index?faces-redirect=true";
	}

	public String vaParaGerenciarAlunos()
	{
		this.page = 6;
		return "index?faces-redirect=true";
	}

	public String vaParaGerenciarRepresentantes()
	{
		this.page = 7;
		return "index?faces-redirect=true";
	}

	public String vaParaGerenciarAdministradores()
	{
		this.page = 8;
		return "index?faces-redirect=true";
	}
	
	public String vaParaGerenciarSistema()
	{
		this.page = 9;
		return "index?faces-redirect=true";
	}

	public Boolean isInAlteracaoCadastral()
	{
		return this.page == 1;
	}
	
	public Boolean isInPreferenciaEscolas()
	{
		return this.page == 2;
	}
	
	public Boolean isInVerEscolaQueFoiAceito()
	{
		return this.page == 3;
	}
	
	public Boolean isInGerenciarEscola()
	{
		return this.page == 4;
	}
	
	public Boolean isInVerAlunos()
	{
		return this.page == 5;
	}
	
	public Boolean isInGerenciarAlunos()
	{
		return this.page == 6;
	}
	
	public Boolean isInGerenciarRepresentantes()
	{
		return this.page == 7;
	}
	
	public Boolean isInGerenciarAdministradores()
	{
		return this.page == 8;
	}
	
	public Boolean isInGerenciarSistema()
	{
		return this.page == 9;
	}
}