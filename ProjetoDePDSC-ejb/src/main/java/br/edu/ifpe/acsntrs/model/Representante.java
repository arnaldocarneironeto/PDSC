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
@DiscriminatorValue("R")
@Table(name = "representante", catalog = "pdscappdatabase", schema = "")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "Representante.findAll", query = "SELECT r FROM Representante r"),
            @NamedQuery(name = "Representante.findByIdrepresentante", query = "SELECT r FROM Representante r WHERE r.id = :id")
        })
public class Representante implements Serializable
{
    private static final long serialVersionUID = 8870004793043691852L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idrepresentante", nullable = false)
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "representante")
    private List<Escola> escolas;

    public Representante()
    {
    }

    public Representante(Integer idrepresentante)
    {
        this.id = idrepresentante;
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
    public List<Escola> getEscolas()
    {
        return escolas;
    }

    public void setEscolas(List<Escola> escolas)
    {
        this.escolas = escolas;
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
        if (!(object instanceof Representante))
        {
            return false;
        }
        Representante other = (Representante) object;
        if ((this.id == null && other.id != null) ||
            (this.id !=
             null &&
             !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "br.edu.ifpe.acsntrs.model.Representante[ idrepresentante=" + id + " ]";
    }
}