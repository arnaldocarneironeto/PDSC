package br.edu.ifpe.acsntrs.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_de_usuario")
@Table(name = "usuario", catalog = "pdscappdatabase", schema = "", uniqueConstraints = {@UniqueConstraint(columnNames = {"Email"}), @UniqueConstraint(columnNames = {"Login"})})
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
            @NamedQuery(name = "Usuario.findById", query = "SELECT u FROM Usuario u WHERE u.id = :id"),
            @NamedQuery(name = "Usuario.findByLogin", query = "SELECT u FROM Usuario u WHERE u.login = :login"),
            @NamedQuery(name = "Usuario.findByEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email"),
            @NamedQuery(name = "Usuario.findByNome", query = "SELECT u FROM Usuario u WHERE u.nome = :nome")
        })
public abstract class Usuario implements Serializable
{
    private static final long serialVersionUID = -3184597509183778732L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_usuario", nullable = false)
    private Integer id;

    @Basic(optional = false)
    @Column(name = "login", nullable = false, length = 45)
    private String login;

    @Basic(optional = false)
    @Column(name = "senha", nullable = false, length = 128)
    private String senha;

    @Basic(optional = false)
    @Column(name = "email", nullable = false, length = 60)
    private String email;

    @Basic(optional = false)
    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    public Usuario()
    {
    }

    public Usuario(Integer id)
    {
        this.id = id;
    }

    public Usuario(Integer id, String login, String senha, String email, String nome)
    {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.email = email;
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

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getSenha()
    {
        return senha;
    }

    public void setSenha(String senha)
    {
        this.senha = senha;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }
    
    public Boolean isAluno()
    {
    	return false;
    }

    public Boolean isRepresentante()
    {
    	return false;
    }

    public Boolean isAdmin()
    {
    	return false;
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
        if (!(object instanceof Usuario))
        {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return nome;
    }
}