package br.edu.ifpe.acsntrs.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

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
            @NamedQuery(name = "Representante.findAll", query = "SELECT p FROM Representante p"),
            @NamedQuery(name = "Representante.findById", query = "SELECT p FROM Representante p WHERE p.id = :id"),
            @NamedQuery(name = "Representante.findByEscola", query = "SELECT p FROM Representante p WHERE p.escola = :escola"),
            @NamedQuery(name = "Representante.findByLogin", query = "SELECT p FROM Representante p WHERE p.login = :login"),
            @NamedQuery(name = "Representante.findByNome", query = "SELECT p FROM Representante p WHERE p.nome = :nome")            
        })
public class Representante extends Usuario
{
    private static final long serialVersionUID = 16244868252988455L;
    
    @OneToOne
    private Escola escola;

    public Representante()
    {
    }

    public Representante(Integer id, String login, String senha, String email, String nome)
    {
        super(id, login, senha, email, nome);
    }

    public Escola getEscola()
    {
        return escola;
    }

    public void setEscola(Escola escola)
    {
        this.escola = escola;
    }
}