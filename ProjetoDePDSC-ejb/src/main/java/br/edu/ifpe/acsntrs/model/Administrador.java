package br.edu.ifpe.acsntrs.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@Entity
@DiscriminatorValue("D")
@Table(name = "administrador", catalog = "pdscappdatabase", schema = "")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "Administrador.findAll", query = "SELECT a FROM Administrador a"),
            @NamedQuery(name = "Administrador.findByIdadministrador", query = "SELECT a FROM Administrador a WHERE a.id = :id")
        })
public class Administrador implements Serializable
{
    private static final long serialVersionUID = -5902767790401836957L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idadministrador", nullable = false)
    private Integer id;

    public Administrador()
    {
    }

    public Administrador(Integer idadministrador)
    {
        this.id = idadministrador;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
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
        if (!(object instanceof Administrador))
        {
            return false;
        }
        Administrador other = (Administrador) object;
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
        return "br.edu.ifpe.acsntrs.model.Administrador[ idadministrador=" +
               id + " ]";
    }
}