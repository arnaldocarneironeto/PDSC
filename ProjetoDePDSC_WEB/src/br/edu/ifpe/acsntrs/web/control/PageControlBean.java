package br.edu.ifpe.acsntrs.web.control;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

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
		// TODO Auto-generated constructor stub
	}
	
	public void vaParaInicio()
	{
		this.page = 0;
	}
	
//	public void vaParaAlteracaoCadastral()
//	{
//		this.page = 1;
//	}
//	
	public String vaParaAlteracaoCadastral()
	{
		return "alteraAluno?faces-redirect=true";
	}

	public Boolean isInAlteracaoCadastral()
	{
		return this.page == 1;
	}
}