package br.edu.ifpe.acsntrs.web.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

import br.edu.ifpe.acsntrs.entity.Aluno;
import br.edu.ifpe.acsntrs.entity.Escola;

@ManagedBean
@SessionScoped
public class EscolaManager implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 174490391411374612L;
	
	private Escola escola;
	private Entry<String, Float> criterio;

	public EscolaManager()
	{
		// TODO Auto-generated constructor stub
		escola = new Escola();
		Map<String, Float> c = new HashMap<>();
		c.put("Matemática", (float) 6.0);
		c.put("Português", (float) 4.0);
		criterio = c.entrySet().iterator().next();
		List<Aluno> alunos_selecionados = new ArrayList<>();
		alunos_selecionados.add(newAluno("Ana", "ana@teste.com", "Português", 8.0, "Matemática", 7.5));
		alunos_selecionados.add(newAluno("Bernardo", "bernardo@teste.com", "Matemática", 6.0, "Física", 6.5));
		alunos_selecionados.add(newAluno("Carla", "carla@teste.com", "Português", 9.0, "Inglês", 8.5));
		escola.setAlunos_selecionados(alunos_selecionados );
		escola.setCriterios(c);
	}

	private Aluno newAluno(String nome, String email, String disciplina1, double d, String disciplina2, double e)
	{
		Aluno a = new Aluno();
		a.setEmail(email);
		a.setNome(nome);
		Map<String, Float> n = new HashMap<>();
		n.put(disciplina1, (float) d);
		n.put(disciplina2, (float) e);
		a.setNotas(n);
		return a;
	}

	public Escola getEscola()
	{
		return escola;
	}

	public void setEscola(Escola escola)
	{
		this.escola = escola;
	}

	public Entry<String, Float> getCriterio()
	{
		return criterio;
	}

	public void setCriterio(Entry<String, Float> criterio)
	{
		this.criterio = criterio;
	}
}