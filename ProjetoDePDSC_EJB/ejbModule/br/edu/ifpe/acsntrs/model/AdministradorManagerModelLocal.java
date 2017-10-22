package br.edu.ifpe.acsntrs.model;

import java.util.List;

import javax.ejb.Local;

import br.edu.ifpe.acsntrs.entity.Administrador;

/**
 * 
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@Local
public interface AdministradorManagerModelLocal
{
	public Administrador save(Administrador admin);

	public Administrador read(Administrador admin);

	public Administrador read(String login, String senha);

	public List<Administrador> read(String nome);

	public void delete(Administrador admin);
}