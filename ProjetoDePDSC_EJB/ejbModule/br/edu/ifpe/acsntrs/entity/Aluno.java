package br.edu.ifpe.acsntrs.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@Entity
@DiscriminatorValue("L")
@Table(name = "aluno", catalog = "pdscappdatabase", schema = "")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "Aluno.findAll", query = "SELECT p FROM Aluno p"),
            @NamedQuery(name = "Aluno.findById", query = "SELECT p FROM Aluno p WHERE p.id = :id"),
            @NamedQuery(name = "Aluno.findByLogin", query = "SELECT p FROM Aluno p WHERE p.login = :login"),
            @NamedQuery(name = "Aluno.findByEmail", query = "SELECT p FROM Aluno p WHERE p.email = :email"),
            @NamedQuery(name = "Aluno.findByNome", query = "SELECT p FROM Aluno p WHERE p.nome = :nome")
        })
public class Aluno extends Usuario
{
    private static final long serialVersionUID = 8317245169749509422L;
    
    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name = "nota")
    @JoinTable(name = "nota", joinColumns = @JoinColumn(name = "id_usuario"))
    @MapKeyColumn(name = "disciplina")
    @Column(name = "nota")
    private Map<String, Float> notas = new HashMap<>();
    
    @ManyToMany
    @OrderColumn(name = "ordem_de_preferencia")
    @JoinColumn(name = "escola_id")
    private List<Escola> preferencia;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "escola_que_selecionou_id")
    private Escola escola_que_selecionou_este_aluno = null;

    public Aluno()
    {
    	this.notas = new HashMap<>();
    	this.preferencia = new ArrayList<>();
    }

    public Aluno(Integer id, String login, String senha, String email, String nome)
    {
        super(id, login, senha, email, nome);
    }

    public Map<String, Float> getNotas()
    {
        return notas;
    }

    public void setNotas(Map<String, Float> notas)
    {
        this.notas = notas;
    }

    public List<Escola> getPreferencia()
    {
        return preferencia;
    }

    public void setPreferencia(List<Escola> preferencia)
    {
        this.preferencia = preferencia;
    }

    public Escola getEscola_que_selecionou_este_aluno()
    {
        return escola_que_selecionou_este_aluno;
    }

    public void setEscola_que_selecionou_este_aluno(Escola escola_que_selecionou_este_aluno)
    {
        this.escola_que_selecionou_este_aluno = escola_que_selecionou_este_aluno;
    }

    public int getRanking(Escola escola)
    {
        return this.preferencia.indexOf(escola);
    }

	@Override
	public Boolean isAluno()
	{
		return true;
	}
}