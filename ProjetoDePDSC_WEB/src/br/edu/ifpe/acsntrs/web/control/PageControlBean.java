package br.edu.ifpe.acsntrs.web.control;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.edu.ifpe.acsntrs.utils.PageEnum;

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
	
	private PageEnum page;
	
	public PageControlBean()
	{
	}
	
	public String vaParaInicio()
	{
		this.page = PageEnum.INICIO;
		return "index?faces-redirect=true";
	}
	
	public String vaParaAlteracaoCadastral()
	{
		this.page = PageEnum.ALTERA_CADASTRO;
		return "index?faces-redirect=true";
	}
	
	public String vaParaPreferenciaEscolas()
	{
		this.page = PageEnum.PREFERENCIAS;
		return "index?faces-redirect=true";
	}
	
	public String vaParaVerEscolaQueFoiAceito()
	{
		this.page = PageEnum.VER_ESCOLA;
		return "index?faces-redirect=true";
	}
	
	public String vaParaGerenciarEscola()
	{
		this.page = PageEnum.GERENCIAR_ESCOLA;
		return "index?faces-redirect=true";
	}

	public String vaParaVerAlunos()
	{
		this.page = PageEnum.VER_ALUNOS;
		return "index?faces-redirect=true";
	}

	public String vaParaGerenciarAlunos()
	{
		this.page = PageEnum.GERENCIAR_ALUNOS;
		return "index?faces-redirect=true";
	}

	public String vaParaGerenciarRepresentantes()
	{
		this.page = PageEnum.GERENCIAR_REPRESENTANTES;
		return "index?faces-redirect=true";
	}

	public String vaParaGerenciarAdministradores()
	{
		this.page = PageEnum.GERENCIAR_ADMINISTRADORES;
		return "index?faces-redirect=true";
	}
	
	public String vaParaGerenciarSistema()
	{
		this.page = PageEnum.GERENCIAR_SISTEMA;
		return "index?faces-redirect=true";
	}

	public Boolean isInAlteracaoCadastral()
	{
		return this.page == PageEnum.ALTERA_CADASTRO;
	}
	
	public Boolean isInPreferenciaEscolas()
	{
		return this.page == PageEnum.PREFERENCIAS;
	}
	
	public Boolean isInVerEscolaQueFoiAceito()
	{
		return this.page == PageEnum.VER_ESCOLA;
	}
	
	public Boolean isInGerenciarEscola()
	{
		return this.page == PageEnum.GERENCIAR_ESCOLA;
	}
	
	public Boolean isInVerAlunos()
	{
		return this.page == PageEnum.VER_ALUNOS;
	}
	
	public Boolean isInGerenciarAlunos()
	{
		return this.page == PageEnum.GERENCIAR_ALUNOS;
	}
	
	public Boolean isInGerenciarRepresentantes()
	{
		return this.page == PageEnum.GERENCIAR_REPRESENTANTES;
	}
	
	public Boolean isInGerenciarAdministradores()
	{
		return this.page == PageEnum.GERENCIAR_ADMINISTRADORES;
	}
	
	public Boolean isInGerenciarSistema()
	{
		return this.page == PageEnum.GERENCIAR_SISTEMA;
	}
}