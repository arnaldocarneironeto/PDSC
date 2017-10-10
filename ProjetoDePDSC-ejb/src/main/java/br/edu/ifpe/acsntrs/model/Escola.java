package br.edu.ifpe.acsntrs.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@Entity
@Table(name = "escola", catalog = "pdscappdatabase", schema = "",
       uniqueConstraints =
       {
           @UniqueConstraint(columnNames = {  "nome" })
       })
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
    private static final long serialVersionUID = 9213356658604711052L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idescola", nullable = false)
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

    @JoinColumn(name = "id_representante", referencedColumnName = "idrepresentante", nullable = false)
    @ManyToOne(optional = false)
    private Representante representante;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "escola")
    private List<Criterio> criterios;

    public Escola()
    {
    }

    public Escola(Integer idescola)
    {
        this.id = idescola;
    }

    public Escola(Integer idescola, String nome)
    {
        this.id = idescola;
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

    public Representante getRepresentante()
    {
        return representante;
    }

    public void setRepresentante(Representante representante)
    {
        this.representante = representante;
    }

    @XmlTransient
    public List<Criterio> getCriterios()
    {
        return criterios;
    }

    public void setCriterios(List<Criterio> criterios)
    {
        this.criterios = criterios;
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
        return "br.edu.ifpe.acsntrs.model.Escola[ idescola=" + id + " ]";
    }
}