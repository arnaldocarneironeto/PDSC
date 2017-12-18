package br.edu.ifpe.acsntrs.web.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
	
	private String nome;
	private String conceito;
	private String descricao;
	private Integer vagas;
	private Escola escola;
	private String nomeCriterio;
	private Float peso;
	private Map<String, Float> criterios;
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
			escola.setRepresentante(loginBean.getRepresentante());
			escola.setAlunos_que_preferem_esta_escola(new ArrayList<>());
			escola.setAlunos_selecionados(new ArrayList<>());
			escola.setCriterios(new HashMap<>());
		}
		nome = escola.getNome();
		conceito = escola.getConceito();
		descricao = escola.getDescricao();
		vagas = escola.getVagas();
		criterios = escola.getCriterios();
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
		escola.setNome(nome);
		escola.setConceito(conceito);
		escola.setDescricao(descricao);
		escola.setVagas(vagas);
		escola.setCriterios(criterios);
		System.out.println(escola.getAlunos_que_preferem_esta_escola());
		
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

	public String getNome()
	{
		return nome;
	}

	public void setNome(String nome)
	{
		this.nome = nome;
	}

	public String getConceito()
	{
		return conceito;
	}

	public void setConceito(String conceito)
	{
		this.conceito = conceito;
	}

	public String getDescricao()
	{
		return descricao;
	}

	public void setDescricao(String descricao)
	{
		this.descricao = descricao;
	}

	public Integer getVagas()
	{
		return vagas;
	}

	public void setVagas(Integer vagas)
	{
		this.vagas = vagas;
	}

	public Map<String, Float> getCriterios()
	{
		return criterios;
	}

	public void setCriterios(Map<String, Float> criterios)
	{
		this.criterios = criterios;
	}
}