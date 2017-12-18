package br.edu.ifpe.acsntrs.web.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.edu.ifpe.acsntrs.entity.Escola;
import br.edu.ifpe.acsntrs.model.EscolaManagerModel;

/**
 * 
 * @author Tássio
 */
@ManagedBean
@SessionScoped
public class EscolaManager implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 174490391411374612L;
	
	private Escola escola;
	private String nomeCriterio;
	private Float peso;
	private Boolean editandoCriterio;
	
	@EJB
	private EscolaManagerModel managerModel;
	
	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;

	public EscolaManager()
	{
		this.editandoCriterio = false;
	}

	public Escola getEscola()
	{
		escola = loginBean.getRepresentante().getEscola();
		if(escola == null)
		{
			escola = new Escola();
			escola.setAlunos_que_preferem_esta_escola(new ArrayList<>());
			escola.setAlunos_selecionados(new ArrayList<>());
			escola.setCriterios(new HashMap<>());
		}
		return escola;
	}
	
	public void novo()
	{
		this.nomeCriterio = "";
		this.peso = 0.0f;
		this.editandoCriterio = true;
	}
	
	public void adicionar()
	{
		this.escola.getCriterios().put(nomeCriterio, peso);
		this.editandoCriterio = false;
	}
	
	public void atualizar(Entry<String, Float> criterio)
	{
		this.nomeCriterio = criterio.getKey();
		this.peso = criterio.getValue();
		this.editandoCriterio = true;
	}
	
	public void cancelar()
	{
		this.editandoCriterio = false;
	}
	
	public void salvar()
	{
		loginBean.getRepresentante().setEscola(escola);
		managerModel.save(escola);
	}
	
	public void excluir(Entry<String, Float> criterio)
	{
		this.escola.getCriterios().remove(criterio.getKey());
	}

	public void setEscola(Escola escola)
	{
		this.escola = escola;
	}

	public LoginBean getLoginBean()
	{
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean)
	{
		this.loginBean = loginBean;
	}

	public Boolean getEditandoCriterio()
	{
		return editandoCriterio;
	}

	public void setEditandoCriterio(Boolean editandoCriterio)
	{
		this.editandoCriterio = editandoCriterio;
	}

	public String getNomeCriterio()
	{
		return nomeCriterio;
	}

	public void setNomeCriterio(String nomeCriterio)
	{
		this.nomeCriterio = nomeCriterio;
	}

	public Float getPeso()
	{
		return peso;
	}

	public void setPeso(Float peso)
	{
		this.peso = peso;
	}
}