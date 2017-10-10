package br.edu.ifpe.acsntrs.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

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
            @NamedQuery(name = "Aluno.findAll", query = "SELECT a FROM Aluno a"),
            @NamedQuery(name = "Aluno.findByIdaluno", query = "SELECT a FROM Aluno a WHERE a.id = :id")
        })
public class Aluno implements Serializable
{
    private static final long serialVersionUID = -5202499588250838845L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idaluno", nullable = false)
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aluno")
    private List<Nota> notas;

    public Aluno()
    {
    }

    public Aluno(Integer idaluno)
    {
        this.id = idaluno;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    @XmlTransient
    public List<Nota> getNotas()
    {
        return notas;
    }

    public void setNotas(List<Nota> notas)
    {
        this.notas = notas;
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
        if (!(object instanceof Aluno))
        {
            return false;
        }
        Aluno other = (Aluno) object;
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
        return "br.edu.ifpe.acsntrs.model.Aluno[ idaluno=" + id + " ]";
    }
}