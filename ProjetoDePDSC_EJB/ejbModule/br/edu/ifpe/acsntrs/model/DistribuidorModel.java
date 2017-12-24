package br.edu.ifpe.acsntrs.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.edu.ifpe.acsntrs.entity.Aluno;
import br.edu.ifpe.acsntrs.entity.Escola;

/**
 * Classe que realiza o processo de distribuição dos alunos entre as escolas
 * segundo o algorítimo de Gale-Shapley.
 * 
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@Stateless
@LocalBean
public class DistribuidorModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 791129664997909649L;

	@PersistenceContext(unitName = "ProjetoDePDSC_PU")
	private EntityManager em;

    private List<Aluno> alunos;
    private List<Escola> escolas;
    
    public DistribuidorModel()
    {
    	resetar();
    }

	public final void resetar()
	{
		try
		{
			this.alunos  = em.createNamedQuery("Aluno.findAll", Aluno.class).getResultList();
		}
		catch (Exception e)
		{
			this.alunos = new ArrayList<>();
		}
		try
		{
			this.escolas = em.createNamedQuery("Escola.findAll", Escola.class).getResultList();
		}
		catch (Exception e)
		{
			this.escolas = new ArrayList<>();
		}
		for(Escola escola: this.escolas)
		{
			for(Aluno aluno: this.alunos)
			{
				if(aluno.getPreferencia().contains(escola) == false)
				{
					aluno.getPreferencia().add(escola);
				}
			}
		}
	}
	
	public void distribuir()
	{
		resetar();
		Map<Escola, List<Aluno>> listagens = new HashMap<>();
        for(Escola escola: this.escolas)
        {
            escola.calculaPreferencias(this.alunos);
            listagens.put(escola, new LinkedList<Aluno>());
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
        
        for(Aluno aluno: alunos)
        {
        	aluno.setEscola_que_selecionou_este_aluno(result.get(aluno));
        }
        
        for(Escola escola: escolas)
        {
        	escola.setAlunos_selecionados(listagens.get(escola));
        }
        
        for(Aluno aluno: alunos)
        {
        	em.persist(aluno);
        }
        
        for(Escola escola: escolas)
        {
        	em.persist(escola);
        }
	}
	
	private void propor(Aluno aluno, final Escola escola, Map<Escola, List<Aluno>> listagens)
    {
        List<Aluno> listagem = listagens.get(escola);
        listagem.add(aluno);
        listagem.sort(getComparator(escola));
        int excedente = listagem.size() >= escola.getVagas()? listagem.size() - escola.getVagas(): 0;
        if(excedente > 0)
        {
            listagem.subList(listagem.size() - excedente, listagem.size()).clear();
        }
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
}