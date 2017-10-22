package br.edu.ifpe.acsntrs.model;

import java.util.List;

import javax.ejb.Local;

import br.edu.ifpe.acsntrs.entity.Aluno;

/**
 * 
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@Local
public interface AlunoManagerModelLocal
{
	public Aluno save(Aluno aluno);

	public Aluno read(Aluno aluno);

	public Aluno read(String login, String senha);

	public List<Aluno> read(String nome);
	
	public List<Aluno> read();

	public void delete(Aluno aluno);
}