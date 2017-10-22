package br.edu.ifpe.acsntrs.web.control;

import java.io.Serializable;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 *
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@ManagedBean(name = "LanguageManager")
@SessionScoped
public class LanguageManagerBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6621011569464359397L;
	private Locale locale;

	public LanguageManagerBean()
	{
		this.locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
	}

	public void mudarLinguagem(Locale local)
	{
		this.locale = local;
		FacesContext context = FacesContext.getCurrentInstance();
		context.getViewRoot().setLocale(local);
	}

	public String english()
	{
		mudarLinguagem(new Locale("en", "US"));
		return null;
	}

	public String portugues()
	{
		mudarLinguagem(new Locale("pt", "BR"));
		return null;
	}

	public Locale getLocale()
	{
		return locale;
	}

	public void setLocale(Locale locale)
	{
		this.locale = locale;
	}
}