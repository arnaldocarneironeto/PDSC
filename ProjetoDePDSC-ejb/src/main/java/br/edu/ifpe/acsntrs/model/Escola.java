package br.edu.ifpe.acsntrs.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@Entity
@Table(name = "escola", catalog = "pdscappdatabase", schema = "", uniqueConstraints = {@UniqueConstraint(columnNames = {"nome"})})
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "Escola.findAll", query = "SELECT e FROM Escola e"),
            @NamedQuery(name = "Escola.findByIdescola", query = "SELECT e FROM Escola e WHERE e.id = :id"),
            @NamedQuery(name = "Escola.findByNome", query = "SELECT e FROM Escola e WHERE e.nome = :nome"),
            @NamedQuery(name = "Escola.findByDescricao", query = "SELECT e FROM Escola e WHERE e.descricao = :descricao"),
            @NamedQuery(name = "Escola.findByConceito", query = "SELECT e FROM Escola e WHERE e.conceito = :conceito")
        })
public class Escola implements Serializable
{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_escola", nullable = false)
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Size(max = 1000)
    @Column(name = "descricao", length = 1000)
    private String descricao;

    @Size(max = 45)
    @Column(name = "conceito", length = 45)
    private String conceito;

    @Min(1)
    private Integer vagas;

    @ElementCollection
    @CollectionTable(name = "criterio")
    @JoinTable(name = "criterio", joinColumns = @JoinColumn(name = "id_escola"))
    @MapKeyColumn(name = "disciplina")
    @Column(name = "peso")
    private Map<String, Float> criterios = new HashMap<>();

    @OneToMany(mappedBy = "escola")
    @JoinColumn(name = "representante_id")
    private List<Representante> representantes;

    @ManyToMany(mappedBy = "preferencia")
    @JoinColumn(name = "aluno_id")
    private List<Aluno> alunos_que_preferem_esta_escola;

    @OneToMany(mappedBy = "escola_que_selecionou_este_aluno")
    @JoinColumn(name = "aluno_selecionado_id")
    private List<Aluno> alunos_selecionados;
    
    @Transient
    private List<Aluno> preferencias_desta_escola;

    @Transient
    private Aluno aluno = null;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public String getDescricao()
    {
        return descricao;
    }

    public void setDescricao(String descricao)
    {
        this.descricao = descricao;
    }

    public String getConceito()
    {
        return conceito;
    }

    public void setConceito(String conceito)
    {
        this.conceito = conceito;
    }

    public Map<String, Float> getCriterios()
    {
        return criterios;
    }

    public void setCriterios(Map<String, Float> criterios)
    {
        this.criterios = criterios;
    }

    public List<Representante> getRepresentantes()
    {
        return representantes;
    }

    public void setRepresentantes(List<Representante> representantes)
    {
        this.representantes = representantes;
    }

    public List<Aluno> getAlunos_que_preferem_esta_escola()
    {
        return alunos_que_preferem_esta_escola;
    }

    public void setAlunos_que_preferem_esta_escola(List<Aluno> alunos_que_preferem_esta_escola)
    {
        this.alunos_que_preferem_esta_escola = alunos_que_preferem_esta_escola;
    }

    public List<Aluno> getAlunos_selecionados()
    {
        return alunos_selecionados;
    }

    public void setAlunos_selecionados(List<Aluno> alunos_selecionados)
    {
        this.alunos_selecionados = alunos_selecionados;
    }

    public Integer getVagas()
    {
        return vagas;
    }

    public void setVagas(Integer vagas)
    {
        this.vagas = vagas;
    }

    public Aluno getAluno()
    {
        return aluno;
    }

    public void setAluno(Aluno aluno)
    {
        this.aluno = aluno;
    }

    public List<Aluno> getPreferencias_desta_escola()
    {
        return preferencias_desta_escola;
    }

    /**
     * Calcula a média do aluno segundo os critérios da escola.
     * Caso o aluno não tenha cursado uma das disciplinas que são usadas como
     * critério pela escola, sua nota nela será 0 (zero).
     * Disciplinas cursadas pelo aluno, mas que não sao critérios da escola, são
     * ignoradas.
     * Caso a escola não tenha fornecido nenhum critério, a nota de todos os
     * alunos será 0 (zero);
     * 
     * @param aluno O Aluno cuja a média se deseja conhecer.
     * @return A média ponderada do aluno segundo os critérios da escola.
     */
    private Double getMediaDoAluno(Aluno aluno)
    {
        Map<String, Float> notas = aluno.getNotas();
        Double acumulador = 0.0;
        Double pesos = 0.0;
        for(Map.Entry<String, Float> criterio: this.criterios.entrySet())
        {
            Float nota = notas.get(criterio.getKey());
            if(nota != null)
            {
                acumulador += notas.get(criterio.getKey()) * criterio.getValue();
                pesos += criterio.getValue();
            }
        }
        return pesos > 0? acumulador / pesos: 0.0;
    }
    
    /**
     * Põe em ordem todos os alunos, segundo as preferências dessa escola.
     * O resultado da ordenação é armazenado no atributo
     * preferencias_desta_escola.
     * 
     * @param alunos lista contendo todos os alunos inscritos.
     */
    public void calculaPreferencias(List<Aluno> alunos)
    {
        this.preferencias_desta_escola = new ArrayList<>();
        Map<Aluno, Double> medias = new HashMap<>();
        for(Aluno aluno: alunos)
        {
            medias.put(aluno, this.getMediaDoAluno(aluno));
        }
        List<Map.Entry<Aluno, Double>> lista = new LinkedList(medias.entrySet());
        lista.sort(getComparator());
        for(Map.Entry<Aluno, Double> par: lista)
        {
            this.preferencias_desta_escola.add(par.getKey());
        }
    }

    private Comparator<Map.Entry<Aluno, Double>> getComparator()
    {
        return new Comparator<Map.Entry<Aluno, Double>>()
        {
            @Override
            public int compare(Map.Entry<Aluno, Double> o1, Map.Entry<Aluno, Double> o2)
            {
                double o1v = o1.getValue();
                double o2v = o2.getValue();
                return o1v > o2v? 1: o1v < o2v? -1: 0;
            }
        };
    }

    @Override
    public int hashCode()
    {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object)
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Escola))
        {
            return false;
        }
        Escola other = (Escola) object;
        if ((this.id == null && other.id != null) ||
            (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "com.arnaldocarneiro.model.Escola[ id=" + id + " ]";
    }
}