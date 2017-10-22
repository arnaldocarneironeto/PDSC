package br.edu.ifpe.acsntrs.model;

import java.util.List;

import javax.ejb.Local;

import br.edu.ifpe.acsntrs.entity.Representante;

/**
 * 
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@Local
public interface RepresentanteManagerModelLocal
{
	public Representante save(Representante rep);

	public Representante read(Representante rep);

	public Representante read(String login, String senha);

	public List<Representante> read(String nome);

	public void delete(Representante rep);
}