package br.edu.ifpe.acsntrs.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@Entity
@Table(name = "nota", catalog = "pdscappdatabase", schema = "",
       uniqueConstraints =
       {
           @UniqueConstraint(columnNames = { "nome" })
       })
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "Nota.findAll", query = "SELECT n FROM Nota n"),
            @NamedQuery(name = "Nota.findByIdnota", query = "SELECT n FROM Nota n WHERE n.id = :id"),
            @NamedQuery(name = "Nota.findByNome", query = "SELECT n FROM Nota n WHERE n.nome = :nome"),
            @NamedQuery(name = "Nota.findByValor", query = "SELECT n FROM Nota n WHERE n.valor = :valor")
        })
public class Nota implements Serializable
{
    private static final long serialVersionUID = -4402270364588359217L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idnota", nullable = false)
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nome", nullable = false, length = 45)
    private String nome;

    @Max(value = 0) @Min(value = 10)
    @Column(name = "valor", precision = 22)
    private Double valor;

    @JoinColumn(name = "id_aluno", referencedColumnName = "idaluno", nullable = false)
    @ManyToOne(optional = false)
    private Aluno aluno;

    public Nota()
    {
    }

    public Nota(Integer idnota)
    {
        this.id = idnota;
    }

    public Nota(Integer idnota, String nome)
    {
        this.id = idnota;
        this.nome = nome;
    }

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

    public Double getValor()
    {
        return valor;
    }

    public void setValor(Double valor)
    {
        this.valor = valor;
    }

    public Aluno getAluno()
    {
        return aluno;
    }

    public void setAluno(Aluno aluno)
    {
        this.aluno = aluno;
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
        if (!(object instanceof Nota))
        {
            return false;
        }
        Nota other = (Nota) object;
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
        return "br.edu.ifpe.acsntrs.model.Nota[ idnota=" + id + " ]";
    }
}