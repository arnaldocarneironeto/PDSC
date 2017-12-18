package br.edu.ifpe.acsntrs.web.control;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import br.edu.ifpe.acsntrs.model.DistribuidorModel;

/**
 * 
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@ManagedBean
@ApplicationScoped
public class DistribuidorBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1180377490082529738L;

	private static final int CADASTRANDO = 0;
	private static final int ORDENANDO = 1;
	private static final int DISTRIBUINDO = 2;
	
	@EJB
	private DistribuidorModel distribuidorModel;
	
	private int status;
    
    public DistribuidorBean()
    {
    	this.status = CADASTRANDO;
    }
    
    public String setCadastrando()
    {
    	this.status = CADASTRANDO;
		return "index?faces-redirect=true";
    }
    
    public String setOrdenando()
    {
    	this.status = ORDENANDO;
		return "index?faces-redirect=true";
    }
    
    public String setDistribuindo()
    {
    	this.status = DISTRIBUINDO;
    	distribuidorModel.distribuir();
		return "index?faces-redirect=true";
    }
    
    public Boolean isCadastrando()
    {
    	return this.status == CADASTRANDO;
    }
    
    public Boolean isOrdenando()
    {
    	return this.status == ORDENANDO;
    }
    
    public Boolean isDistribuindo()
    {
    	return this.status == DISTRIBUINDO;
    }
}