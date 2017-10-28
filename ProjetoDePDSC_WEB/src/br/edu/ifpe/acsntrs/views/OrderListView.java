package br.edu.ifpe.acsntrs.views;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import br.edu.ifpe.acsntrs.entity.Aluno;
import br.edu.ifpe.acsntrs.entity.Escola;
import br.edu.ifpe.acsntrs.model.AlunoManagerModel;
import br.edu.ifpe.acsntrs.model.EscolaManagerModel; 

@RequestScoped 
@ManagedBean
public class OrderListView {
     
    @EJB
    private EscolaManagerModel escolaManagerModel;
    @EJB
    private AlunoManagerModel alunoManagerModel;
    
    private List<Escola> escolas;
     
    @PostConstruct
    public void init() {
    	escolas = escolaManagerModel.read();
    }
 
    public void atualizarPreferencia(String login) {
    	if(login == null) return;
    	List<Aluno> alunos = alunoManagerModel.read();
    	for(Aluno aluno: alunos)
    	{
    		if(login.equals(aluno.getLogin()))
    		{
    			aluno.setPreferencia(escolas);
    			alunoManagerModel.save(aluno);
    			break;
    		}
    	}
    }
 
    public List<Escola> getEscolas() {
		return escolas;
	}


	public void setEscolas(List<Escola> escolas) {
		this.escolas = escolas;
	}

 
    public void onSelect(SelectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().toString()));
    }
     
    public void onUnselect(UnselectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().toString()));
    }
     
    public void onReorder() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
    } 
}