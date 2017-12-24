package br.edu.ifpe.acsntrs.views;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import br.edu.ifpe.acsntrs.entity.Aluno;
import br.edu.ifpe.acsntrs.entity.Escola;
import br.edu.ifpe.acsntrs.model.AlunoManagerModel;
import br.edu.ifpe.acsntrs.model.EscolaManagerModel;
import br.edu.ifpe.acsntrs.web.control.LoginBean;

/**
 * 
 * @author Tássio
 */
@RequestScoped
@ManagedBean
public class OrderListView
{
	@EJB
	private EscolaManagerModel escolaManagerModel;

	@EJB
	private AlunoManagerModel alunoManagerModel;

	@ManagedProperty("#{loginBean}")
	private LoginBean loginBean;

	private List<Escola> escolas;

	@PostConstruct
	public void init()
	{
		List<Escola> el = escolaManagerModel.read();
		if(loginBean.getAluno() != null)
		{
			loginBean.setAluno(alunoManagerModel.readFull(loginBean.getAluno()));
			if(loginBean.getAluno().getPreferencia() != null)
			{
				escolas = loginBean.getAluno().getPreferencia();
			}
			else
			{
				escolas = new ArrayList<>();
			}
		}
		else
		{
			escolas = new ArrayList<>();
		}
		for(Escola e: el)
		{
			if(escolas.contains(e) == false)
			{
				escolas.add(e);
			}
		}
	}

	public void atualizarPreferencia(Aluno aluno)
	{
		aluno.setPreferencia(escolas);
		alunoManagerModel.save(aluno);
	}

	public List<Escola> getEscolas()
	{
		return escolas;
	}

	public void setEscolas(List<Escola> escolas)
	{
		this.escolas = escolas;
	}

	public LoginBean getLoginBean()
	{
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean)
	{
		this.loginBean = loginBean;
	}
}