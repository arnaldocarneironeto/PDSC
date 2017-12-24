package br.edu.ifpe.acsntrs.web.control;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.edu.ifpe.acsntrs.entity.Aluno;
import br.edu.ifpe.acsntrs.model.AlunoManagerModel;

/**
 * 
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@ManagedBean
@SessionScoped
public class AlteraAluno implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4457500300767152557L;
	
	private String senha;
	
	private String confSenha;
	
	private Aluno aluno;
	
	private Boolean editandoNota;
	
	private String nomeDisciplina;
	
	private Float notaDisciplina;
	
	private Map<String, Float> notas;
	
	@EJB
	private AlunoManagerModel managerModel;
	
	public AlteraAluno()
	{
		this.editandoNota = false;
	}
	
	public String salvar(Aluno aluno)
	{
		if(senha != null && senha.equals(confSenha))
		{
			aluno.setSenha(senha);
			aluno.setNome(this.aluno.getNome());
			aluno.setEmail(this.aluno.getEmail());
			aluno.setNotas(this.aluno.getNotas());
			managerModel.save(aluno);			
		}
		return "index?faces-redirect=true";
	}
	
	public void nova()
	{
		this.nomeDisciplina = "";
		this.notaDisciplina = 0f;
		this.editandoNota = true;
	}
	
	public void atualizar(Entry<String, Float> nota)
	{
		this.nomeDisciplina = nota.getKey();
		this.notaDisciplina = nota.getValue();
		this.editandoNota = true;
	}
	
	public void excluir(Entry<String, Float> nota)
	{
		notas.remove(nota.getKey());
	}
	
	public void cancelar()
	{
		this.nomeDisciplina = "";
		this.notaDisciplina = 0f;
		this.editandoNota = false;
	}
	
	public void adicionar()
	{
		notas.put(nomeDisciplina, notaDisciplina);
		this.editandoNota = false;
	}

	public Aluno getAluno()
	{
		return aluno;
	}

	public void setAluno(Aluno aluno)
	{
		this.aluno = aluno;
	}

	public String getSenha()
	{
		return senha;
	}

	public void setSenha(String senha)
	{
		this.senha = senha;
	}

	public String getConfSenha()
	{
		return confSenha;
	}

	public void setConfSenha(String confSenha)
	{
		this.confSenha = confSenha;
	}

	public Boolean getEditandoNota()
	{
		return editandoNota;
	}

	public void setEditandoNota(Boolean editandoNota)
	{
		this.editandoNota = editandoNota;
	}

	public Map<String, Float> getNotas()
	{
		return notas;
	}

	public void setNotas(Map<String, Float> notas)
	{
		this.notas = notas;
	}

	public String getNomeDisciplina()
	{
		return nomeDisciplina;
	}

	public void setNomeDisciplina(String nomeDisciplina)
	{
		this.nomeDisciplina = nomeDisciplina;
	}

	public Float getNotaDisciplina()
	{
		return notaDisciplina;
	}

	public void setNotaDisciplina(Float notaDisciplina)
	{
		this.notaDisciplina = notaDisciplina;
	}
}