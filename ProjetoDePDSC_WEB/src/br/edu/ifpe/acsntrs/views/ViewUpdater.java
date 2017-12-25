package br.edu.ifpe.acsntrs.views;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import br.edu.ifpe.acsntrs.entity.Aluno;
import br.edu.ifpe.acsntrs.entity.Escola;
import br.edu.ifpe.acsntrs.entity.Representante;
import br.edu.ifpe.acsntrs.entity.Usuario;
import br.edu.ifpe.acsntrs.model.EscolaManagerModel;
import br.edu.ifpe.acsntrs.web.control.AlteraAluno;
import br.edu.ifpe.acsntrs.web.control.EscolaManager;

/**
 * 
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@ManagedBean
@SessionScoped
public class ViewUpdater
{
	@EJB
	private EscolaManagerModel escolaManagerModel;
	
	@ManagedProperty(value = "#{alteraAluno}")
	private AlteraAluno alteraAluno;
	
	@ManagedProperty(value = "#{escolaManager}")
	private EscolaManager escolaManager;
	
	public void atualizaView(Usuario usuario)
	{
		if(usuario != null)
		{
			if(usuario instanceof Aluno)
			{
				Aluno aluno = (Aluno) usuario;
				alteraAluno.setNotas(aluno.getNotas() != null? aluno.getNotas(): new HashMap<>());
			}
			if(usuario instanceof Representante)
			{
				Representante representante = (Representante) usuario;
				Escola escola = representante.getEscola();
				if(escola == null)
				{
					escola = new Escola();
				}
				escola = escolaManagerModel.read(escola);
				escolaManager.setNome(escola.getNome());
				escolaManager.setConceito(escola.getConceito());
				escolaManager.setDescricao(escola.getDescricao());
				escolaManager.setVagas(escola.getVagas());
				escolaManager.setCriterios(escola.getCriterios() != null? escola.getCriterios(): new HashMap<>());
				escolaManager.setAlunosSelecionados(escola.getAlunos_selecionados() != null? escola.getAlunos_selecionados(): new ArrayList<>());
			}
		}
	}

	public AlteraAluno getAlteraAluno()
	{
		return alteraAluno;
	}

	public void setAlteraAluno(AlteraAluno alteraAluno)
	{
		this.alteraAluno = alteraAluno;
	}

	public EscolaManager getEscolaManager()
	{
		return escolaManager;
	}

	public void setEscolaManager(EscolaManager escolaManager)
	{
		this.escolaManager = escolaManager;
	}
}