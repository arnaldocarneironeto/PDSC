package br.edu.ifpe.acsntrs.web.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

import br.edu.ifpe.acsntrs.entity.Aluno;
import br.edu.ifpe.acsntrs.entity.Escola;
import br.edu.ifpe.acsntrs.model.AlunoManagerModel;
import br.edu.ifpe.acsntrs.model.EscolaManagerModel;

/**
 * Classe que realiza o processo de distribuição dos alunos entre as escolas
 * segundo o algorítimo de Gale-Shapley.
 * 
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@ManagedBean(name = "DistribuidorBean")
@RequestScoped
public class DistribuidorBean implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8548005854196440955L;

	private List<Aluno> alunos;
	
    private List<Escola> escolas;
    
    private Map<Aluno, Escola> distribuicao;
    
    @EJB
	private AlunoManagerModel alunoManagerModel;
    
    @EJB
    private EscolaManagerModel escolaManagerModel;
    
	public DistribuidorBean()
	{
	}
	
	@PostConstruct
	public void inicializar()
	{
		this.alunos = alunoManagerModel.read();
		this.escolas = escolaManagerModel.read();
		this.distribuicao = new HashMap<>();
	}
	
	public void distribuir()
	{
		this.distribuicao = new HashMap<>();
		Map<Escola, List<Aluno>> listagens = new HashMap<>();
		for(Escola escola: this.escolas)
		{
			escola.calculaPreferencias(this.alunos);
			listagens.put(escola, new LinkedList<Aluno>());

			List<Aluno> alunosSelecionados = new ArrayList<>();
			escola.setAlunos_selecionados(alunosSelecionados);
		}

		Map<Aluno, Integer> ultimaEscolaProposta = new HashMap<>();
		for(Aluno aluno: this.alunos)
		{
			ultimaEscolaProposta.put(aluno, -1);
		}

		boolean concluido = false;

		while(concluido == false)
		{
			concluido = true;
			for(Aluno aluno: this.alunos)
			{
				if(foraDasListagensListagem(aluno, listagens))
				{
					int proximaEscola = ultimaEscolaProposta.get(aluno) + 1;
					if(proximaEscola < aluno.getPreferencia().size())
					{
						Escola escola = aluno.getPreferencia().get(proximaEscola);
						propor(aluno, escola, listagens);
						ultimaEscolaProposta.put(aluno, proximaEscola);
						concluido = false;
					}
				}
			}
		}

		Map<Aluno, Escola> result = new HashMap<>();
		for(Map.Entry<Escola, List<Aluno>> listagem: listagens.entrySet())
		{
			for(Aluno aluno: listagem.getValue())
			{
				result.put(aluno, listagem.getKey());
			}
		}

		for(Aluno aluno: this.alunos)
		{
			Escola escola = result.get(aluno);
			aluno.setEscola_que_selecionou_este_aluno(escola);
			if(escola != null)
			{
				List<Aluno> alunosSelecionadosPelaEscola = escola.getAlunos_selecionados();
				alunosSelecionadosPelaEscola.add(aluno);
				escola.setAlunos_selecionados(alunosSelecionadosPelaEscola);
			}
			alunoManagerModel.save(aluno);
		}
		
		for(Escola escola: this.escolas)
		{
			escolaManagerModel.save(escola);
		}
	}

	private void propor(Aluno aluno, final Escola escola, Map<Escola, List<Aluno>> listagens)
	{
		System.out.println(aluno + " se inscreve para " + escola);
		List<Aluno> listagem = listagens.get(escola);
		listagem.add(aluno);
		listagem.sort(getComparator(escola));
		int excedente = listagem.size() >= escola.getVagas()? listagem.size() - escola.getVagas(): 0;
		if(excedente > 0)
		{
			listagem.subList(listagem.size() - excedente, listagem.size()).clear();
		}
		System.out.println(escola + " = " + listagem + "\n");
	}

	private Comparator<Aluno> getComparator(final Escola escola)
	{
		return new Comparator<Aluno>()
		{
			@Override
			public int compare(Aluno o1, Aluno o2)
			{
				return escola.getPreferencias_desta_escola().indexOf(o1) - escola.getPreferencias_desta_escola().indexOf(o2);
			}
		};
	}

	private boolean foraDasListagensListagem(Aluno aluno, Map<Escola, List<Aluno>> listagens)
	{
		for(Map.Entry<Escola, List<Aluno>> listagem: listagens.entrySet())
		{
			if(listagem.getValue().contains(aluno))
			{
				return false;
			}
		}
		return true;
	}

	public List<Aluno> getAlunos()
	{
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos)
	{
		this.alunos = alunos;
	}

	public List<Escola> getEscolas()
	{
		return escolas;
	}

	public void setEscolas(List<Escola> escolas)
	{
		this.escolas = escolas;
	}

	public Map<Aluno, Escola> getDistribuicao()
	{
		return distribuicao;
	}

	public void setDistribuicao(Map<Aluno, Escola> distribuicao)
	{
		this.distribuicao = distribuicao;
	}
}