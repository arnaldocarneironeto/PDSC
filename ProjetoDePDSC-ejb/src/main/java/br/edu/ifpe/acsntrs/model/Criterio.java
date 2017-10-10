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
@Table(name = "criterio", catalog = "pdscappdatabase", schema = "",
       uniqueConstraints =
       {
           @UniqueConstraint(columnNames = { "disciplina" })
       })
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "Criterio.findAll", query = "SELECT c FROM Criterio c"),
            @NamedQuery(name = "Criterio.findByIdcriterio", query = "SELECT c FROM Criterio c WHERE c.id = :id"),
            @NamedQuery(name = "Criterio.findByDisciplina", query = "SELECT c FROM Criterio c WHERE c.disciplina = :disciplina"),
            @NamedQuery(name = "Criterio.findByPeso", query = "SELECT c FROM Criterio c WHERE c.peso = :peso")
        })
public class Criterio implements Serializable
{
    private static final long serialVersionUID = -2081351778498186686L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idcriterio", nullable = false)
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "disciplina", nullable = false, length = 100)
    private String disciplina;

    @Max(value = 0) @Min(value = 10)
    @Column(name = "peso", precision = 12)
    private Double peso;

    @JoinColumn(name = "id_escola", referencedColumnName = "idescola", nullable = false)
    @ManyToOne(optional = false)
    private Escola escola;

    public Criterio()
    {
    }

    public Criterio(Integer idcriterio)
    {
        this.id = idcriterio;
    }

    public Criterio(Integer idcriterio, String disciplina)
    {
        this.id = idcriterio;
        this.disciplina = disciplina;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getDisciplina()
    {
        return disciplina;
    }

    public void setDisciplina(String disciplina)
    {
        this.disciplina = disciplina;
    }

    public Double getPeso()
    {
        return peso;
    }

    public void setPeso(Double peso)
    {
        this.peso = peso;
    }

    public Escola getEscola()
    {
        return escola;
    }

    public void setEscola(Escola escola)
    {
        this.escola = escola;
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
        if (!(object instanceof Criterio))
        {
            return false;
        }
        Criterio other = (Criterio) object;
        if ((this.id == null && other.id != null) ||
            (this.id != null && !this.id.
             equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "br.edu.ifpe.acsntrs.model.Criterio[ idcriterio=" + id +
               " ]";
    }
}