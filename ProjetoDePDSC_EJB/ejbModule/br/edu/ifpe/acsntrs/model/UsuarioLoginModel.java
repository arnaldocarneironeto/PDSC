package br.edu.ifpe.acsntrs.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import br.edu.ifpe.acsntrs.entity.Administrador;
import br.edu.ifpe.acsntrs.entity.Aluno;
import br.edu.ifpe.acsntrs.entity.Escola;
import br.edu.ifpe.acsntrs.entity.Representante;
import br.edu.ifpe.acsntrs.entity.Usuario;
import br.edu.ifpe.acsntrs.utils.Triple;
import br.edu.ifpe.acsntrs.utils.WebServiceUtils;

/**
 * Session Bean implementation class UsuarioLoginModel
 * 
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
@Stateless
@LocalBean
public class UsuarioLoginModel implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7940464812308512472L;
	
	@PersistenceContext(unitName = "ProjetoDePDSC_PU")
	private EntityManager em;

	public UsuarioLoginModel()
	{
	}

	public Usuario getUsuario(String login, String senha)
	{
		try
		{
			Usuario usuario = em.createNamedQuery("Usuario.findByLogin", Usuario.class).setParameter("login", login).getSingleResult();
			if(usuario != null && senha.equals(usuario.getSenha()))
			{
				return usuario;
			}
			return null;
		}
		catch(NoResultException e)
		{
			return null;
		}
	}
	
	public Boolean adminExists()
	{
		try
		{
			int count = ((Number) em.createNamedQuery("Administrador.countAll").getSingleResult()).intValue();
			return count > 0;
		}
		catch(NoResultException e)
		{
			return false;
		}
	}

	private static final int NUMERO_DE_ESCOLAS = 10;
	private static final int NUMERO_DE_ALUNOS = 350;

	public void createDefaultAdmin()
	{
		Administrador admin = new Administrador();
		admin.setNome("Administrador");
		admin.setLogin("admin");
		admin.setEmail("admin@sdvea");
		admin.setSenha("admin");
		em.persist(admin);
		
		List<String> names = new ArrayList<>();
		List<String> emails = new ArrayList<>();
		for(int i = 0; i < NUMERO_DE_ESCOLAS; ++i)
		{
			Triple<String, String, String> triplaNomeEmailGenero = WebServiceUtils.getRandomTripleNameEmailGender();
			if(triplaNomeEmailGenero.getLeft().equals("") == false)
            {
                names.add(triplaNomeEmailGenero.getLeft());
                emails.add(triplaNomeEmailGenero.getMiddle());
            }
		}
		if(names.size() != NUMERO_DE_ESCOLAS)
		{
            names = Arrays.asList("Gabrielly Almeida Fernandes", "Julian Fernandes Melo", "Laura Pinto Gomes", "Ana Silva Martins", "Giovanna Costa Almeida", "Danilo Pereira Araujo", "Miguel Souza Correia", "Breno Araujo Melo", "Pedro Pereira Carvalho", "Rafael Correia Barbosa");
            emails = Arrays.asList("GabriellyAlmeidaFernandes@rhyta.com", "JulianFernandesMelo@dayrep.com", "LauraPintoGomes@jourrapide.com", "AnaSilvaMartins@teleworm.us", "GiovannaCostaAlmeida@teleworm.us", "DaniloPereiraAraujo@jourrapide.com", "MiguelSouzaCorreia@armyspy.com", "BrenoAraujoMelo@dayrep.com", "PedroPereiraCarvalho@rhyta.com", "RafaelCorreiaBarbosa@armyspy.com");
		}
		
		Random rnd = new Random();
		String[] conceitos = {"A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "D-", "E+", "E", "E-", "F+", "F"};
		String[] descricoes = {"Lorem ipsum dolor sit amet, ex inani quaeque voluptatum has, quo quas simul graecis ne. Scribentur reformidans dissentiunt duo no, brute labores reprehendunt eu has. Duo ut offendit constituam. Et vel tota maluisset sententiae, fastidii appareat atomorum ea mea.",
				               "Hinc iudico quaeque eum eu. Mazim evertitur usu et. Ei ius consulatu intellegat temporibus, ad etiam cetero vis, at verterem vituperata mel. Ei usu nibh latine, pri assum abhorreant incorrupte no. Id nam hinc aeterno, ad sit veniam elaboraret disputando. Vis ea vivendo gubergren.",
				               "Unum brute appellantur id quo, ius an unum petentium instructior. Usu prompta disputationi in, nostrud scripserit quo te, sed eu congue detraxit. Possim aliquid deleniti nec no, aliquid petentium consulatu ius ne. Mea te periculis instructior. Mel et hendrerit mnesarchum, dolorum vivendo philosophia an sed. Ius quot porro ne.",
				               "Has augue debet te. Vim ea augue nulla, his utinam ancillae no. Eu vel omnium diceret postulant. No eum novum corpora, principes persecuti sit ne. Sed ne causae platonem qualisque, vidit scaevola pericula no mel. Ea cum graeco mollis timeam, ut dicta soleat constituam est.",
				               "Vel feugait corrumpit instructior in, te graecis cotidieque consectetuer vis, porro causae eu has. Vis modus dicit timeam cu, ne vim omnis persius accusata, no eum impetus labores eligendi. Mei at copiosae vivendum quaerendum, cu mei animal feugiat. Per ad iudico eripuit, ferri magna conceptam te vim, est nisl augue ignota no. Tibique mediocrem percipitur his ne, ea viris oportere cum. Denique consequat in vix. Eos wisi inani prompta in, no usu tamquam intellegat."};
		String[] disciplinas = {"Matemática", "Português", "Geografia", "História", "Química", "Física", "Filosofia"};
		String[] nomes = {"Ana", "Bartolomeu", "Carla", "Daniele", "Everton", "Francine", "Gustavo", "Helga", "Italo", "Joana", "Lucas", "Maria"};
		String[] sobrenomes = {"Almeida", "Fernandes", "Melo", "Pinto", "Gomes", "Silva", "Martins", "Costa"};
		
		for(int i = 0; i < names.size(); ++i)
		{
			Representante rep = new Representante();
			rep.setNome(names.get(i));
			rep.setEmail(emails.get(i));
			rep.setLogin("rep" + (i + 1));
			rep.setSenha(rep.getLogin());
			em.persist(rep);
			
			Escola escola = new Escola();
			escola.setNome("Escola " + (i + 1));
			escola.setConceito(conceitos[rnd.nextInt(conceitos.length / 4) + rnd.nextInt(conceitos.length / 4) + rnd.nextInt(conceitos.length / 4) + rnd.nextInt(conceitos.length / 4)]);
			escola.setDescricao(descricoes[rnd.nextInt(descricoes.length)]);
			escola.setVagas(10 + rnd.nextInt(5) * 5);
			escola.setAlunos_que_preferem_esta_escola(new ArrayList<>());
			escola.setAlunos_selecionados(new ArrayList<>());
			Map<String, Float> criterios = new HashMap<>();
			for(int j = 0; j < rnd.nextInt(disciplinas.length); ++j)
			{
				criterios.put(disciplinas[rnd.nextInt(disciplinas.length)], (1 + rnd.nextInt(10)) / 10f);
			}
			escola.setCriterios(criterios);
			
			escola.setRepresentante(rep);
			rep.setEscola(escola);
			em.persist(escola);
			em.merge(rep);
		}
		
		names = new ArrayList<>();
		emails = new ArrayList<>();
		for(int i = 0; i < NUMERO_DE_ALUNOS; ++i)
		{
			Triple<String, String, String> triplaNomeEmailGenero = WebServiceUtils.getRandomTripleNameEmailGender();
			if(triplaNomeEmailGenero.getLeft().equals("") == false)
            {
                names.add(triplaNomeEmailGenero.getLeft());
                emails.add(triplaNomeEmailGenero.getMiddle());
            }
		}
		while(names.size() < NUMERO_DE_ALUNOS)
		{
			String n;
			do
			{
				n = nomes[rnd.nextInt(nomes.length)] + " " + sobrenomes[rnd.nextInt(sobrenomes.length)] + " " + sobrenomes[rnd.nextInt(sobrenomes.length)] + " " + sobrenomes[rnd.nextInt(sobrenomes.length)] + " " + sobrenomes[rnd.nextInt(sobrenomes.length)];
			}
			while(names.contains(n));
			names.add(n);
			emails.add(n.replace(" ","") + "@aluno.com.br");
		}
		
		for(int i = 0; i < names.size(); ++i)
		{
			Aluno aluno = new Aluno();
			aluno.setNome(names.get(i));
			aluno.setEmail(emails.get(i));
			aluno.setLogin("aluno" + (i + 1));
			aluno.setSenha(aluno.getLogin());
			Map<String, Float> notas = new HashMap<>();
			for(int j = 0; j < rnd.nextInt(disciplinas.length); ++j)
			{
				notas.put(disciplinas[rnd.nextInt(disciplinas.length)], rnd.nextInt(101) / 10f);
			}
			aluno.setNotas(notas);
			em.persist(aluno);
		}
	}
}