package br.edu.ifpe.acsntrs.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
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
            @NamedQuery(name = "Administrador.findById", query = "SELECT a FROM Administrador a WHERE a.id = :id"),
            @NamedQuery(name = "Administrador.findByLogin", query = "SELECT a FROM Administrador a WHERE a.login = :login"),
            @NamedQuery(name = "Administrador.findByEmail", query = "SELECT a FROM Administrador a WHERE a.email = :email"),
            @NamedQuery(name = "Administrador.findByNome", query = "SELECT a FROM Administrador a WHERE a.nome = :nome")
        })
public class Administrador extends Usuario
{
    private static final long serialVersionUID = -7578967122739714611L;

    public Administrador()
    {
    }

    public Administrador(Integer id, String login, String senha, String email, String nome)
    {
        super(id, login, senha, email, nome);
    }
}