package br.edu.ifpe.acsntrs.model;

import java.util.List;

import javax.ejb.Local;

import br.edu.ifpe.acsntrs.entity.Escola;

/**
 * 
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@Local
public interface EscolaManagerModelLocal
{
	public Escola save(Escola escola);

	public Escola read(Escola escola);

	public List<Escola> read(String nome);

	public void delete(Escola escola);
}