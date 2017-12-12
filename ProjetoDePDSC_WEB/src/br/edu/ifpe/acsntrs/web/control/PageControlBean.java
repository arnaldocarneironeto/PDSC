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
	
	private String page;
	
	public PageControlBean()
	{
		// TODO Auto-generated constructor stub
	}
	
	public void vaParaInicio()
	{
		this.page = "";
	}
	
	public void vaParaAlteracaoCadastral()
	{
		this.page = "crud";
	}

	public String getPage()
	{
		return page;
	}

	public void setPage(String page)
	{
		this.page = page;
	}
}