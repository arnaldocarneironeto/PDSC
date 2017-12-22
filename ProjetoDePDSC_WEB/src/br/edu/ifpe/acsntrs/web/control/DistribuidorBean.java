package br.edu.ifpe.acsntrs.web.control;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import br.edu.ifpe.acsntrs.model.DistribuidorModel;
import br.edu.ifpe.acsntrs.utils.SystemStateEnum;

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
	
	@EJB
	private DistribuidorModel distribuidorModel;
	
	private SystemStateEnum status;
    
    public DistribuidorBean()
    {
    	this.status = SystemStateEnum.CADASTRANDO;
    }
    
    public String setCadastrando()
    {
    	this.status = SystemStateEnum.CADASTRANDO;
		return "index?faces-redirect=true";
    }
    
    public String setOrdenando()
    {
    	this.status = SystemStateEnum.ORDENANDO;
		return "index?faces-redirect=true";
    }
    
    public String setDistribuindo()
    {
    	this.status = SystemStateEnum.DISTRIBUINDO;
    	distribuidorModel.distribuir();
		return "index?faces-redirect=true";
    }
    
    public Boolean isCadastrando()
    {
    	return this.status == SystemStateEnum.CADASTRANDO;
    }
    
    public Boolean isOrdenando()
    {
    	return this.status == SystemStateEnum.ORDENANDO;
    }
    
    public Boolean isDistribuindo()
    {
    	return this.status == SystemStateEnum.DISTRIBUINDO;
    }
}