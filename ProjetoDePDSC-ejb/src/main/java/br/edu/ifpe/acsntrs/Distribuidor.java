package br.edu.ifpe.acsntrs;

import br.edu.ifpe.acsntrs.model.Aluno;
import br.edu.ifpe.acsntrs.model.Escola;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Classe que realiza o processo de distribuição dos alunos entre as escolas
 * segundo o algorítimo de Gale-Shapley.
 * 
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
public final class Distribuidor
{
    private List<Aluno> alunos;
    private List<Escola> escolas;
    
    public Distribuidor()
    {
    }
    
    public Distribuidor(List<Aluno> alunos, List<Escola> escolas)
    {
        super();
        this.resetar(alunos, escolas);
    }
    
    public void resetar(List<Aluno> alunos, List<Escola> escolas)
    {
        this.alunos = alunos;
        this.escolas = escolas;
    }

    private Map<Aluno, Escola> distribuir()
    {
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
        return result;
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

    // Métodos para teste
    public static void main(String[] args)
    {
        Aluno ana = criarAluno(0, "ana", "", "a@b.c", "Ana", "Português", 10, "Matemática", 8.5f, "Educação Física", 9);
        Aluno beth = criarAluno(1, "beth", "", "b@b.c", "Beth", "Português", 9, "Matemática", 9.5f, "Educação Física", 9.5f);
        
        Escola ducandario = criarEscola(0, "Ducandario", 2, "Português", 4, "Matemática", 6, "Geografia", 1);
        Escola elemental = criarEscola(1, "Elemental", 1, "Geografia", 9, "Matemática", 1, "Ginástica Rítmica", 1);

        List<Escola> anapref = new ArrayList<>();
        anapref.add(elemental);
        anapref.add(ducandario);
        ana.setPreferencia(anapref);

        List<Escola> bethpref = new ArrayList<>();
        bethpref.add(elemental);
        bethpref.add(ducandario);
        beth.setPreferencia(bethpref);

        List<Escola> escolas = new ArrayList<>();
        escolas.add(elemental);
        escolas.add(ducandario);

        List<Aluno> alunos = new ArrayList<>();
        alunos.add(ana);
        alunos.add(beth);
        
        Distribuidor d = new Distribuidor(alunos, escolas);
        
        System.out.println(d.distribuir());
        System.out.println("Ok");
    }

    private static Escola criarEscola(int id, final String nome, int vagas, final String criterio1, final float peso1, final String criterio2, final float peso2, final String criterio3, final float peso3)
    {
        Escola escola = new Escola();
        escola.setConceito("jbjhbj");
        escola.setDescricao("uma escola");
        escola.setId(id);
        escola.setNome(nome);
        escola.setVagas(vagas);
        Map<String, Float> criterios = new HashMap<>();
        criterios.put(criterio1, peso1);
        criterios.put(criterio2, peso2);
        criterios.put(criterio3, peso3);
        escola.setCriterios(criterios);
        return escola;
    }

    private static Aluno criarAluno(int id, String login, String senha, String email, String nome, final String materia1, final float nota1, final String materia2, final float nota2, final String materia3, final float nota3)
    {
        Aluno aluno = new Aluno(id, login, senha, email, nome);
        final HashMap<String, Float> notas = new HashMap<>();
        notas.put(materia1, nota1);
        notas.put(materia2, nota2);
        notas.put(materia3, nota3);
        aluno.setNotas(notas);
        return aluno;
    }
}