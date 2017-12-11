package br.edu.ifpe.acsntrs.views;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
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
    	List<Escola> escolas = new ArrayList<>();
		escolas.add(newEscola("Educandário Seu Lunga", "Lorem ipsum dolor sit amet, his ad fabellas lobortis, ea nusquam phaedrum perpetua pri. Cu liber consulatu quo, duo cu eros viderer electram. Ne maluisset laboramus mei, illud laudem ius ne. No mediocrem iudicabit vis, cu sapientem gloriatur ius, tamquam malorum his ad. Summo probatus deserunt at sea.", "A"));
		escolas.add(newEscola("Escolinha do Professor Raimundo", "Vituperata neglegentur philosophia has ne, mazim eligendi interpretaris ut cum. Vix elit suscipit delectus et, ius no alterum comprehensam. Definiebas signiferumque vel ex. Dicit partiendo urbanitas at ius, ne discere recteque duo. Mel at movet impetus recusabo, eu per facilisi scripserit.", "B-"));
		escolas.add(newEscola("Colégio São Longuinho", "Ex ipsum viris clita vis, pro et quot facer soluta. Pri admodum comprehensam id, duo an exerci singulis assueverit, eu est doctus nominati conceptam. Doctus civibus sit in, te unum ridens laboramus vix. Et pro epicuri tincidunt philosophia, vitae incorrupte cu duo.", "A-"));
		escolas.add(newEscola("Universidade de Mata a Chute", "Diam senserit an vis, sanctus luptatum ius an. Prima gubergren persecuti qui cu, id sit eleifend deseruisse expetendis, qui clita abhorreant suscipiantur ut. Ne sit quot indoctum argumentum, ex ius epicurei recusabo. Eos libris causae eruditi ex. Delicata incorrupte vim ad, ea mea fuisset nostrum offendit. Nam iriure sensibus id, ea volumus sensibus constituto vel.", "A+"));
		return escolas;
	}

    //Apagar
	private Escola newEscola(String nome, String descricao, String conceito)
	{
		Escola escola = new Escola();
		escola.setNome(nome);
		escola.setDescricao(descricao);
		escola.setConceito(conceito);
		return escola;
	}

	public void setEscolas(List<Escola> escolas) {
		this.escolas = escolas;
	}

    public void onSelect(SelectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item selecionado", event.getObject().toString()));
    }     
}