package br.edu.ifpe.acsntrs.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
public class WebServiceUtils
{
	private static int count = 0;
	private static String[] nomes = {"Vinicius Araujo Cavalcanti", "Marina Ribeiro Almeida", "Sarah Araujo Alves", "Ant�nio Cunha Martins", "Guilherme Cunha Santos", "Bruna Ferreira Pereira", "Caio Souza Barbosa", "Eduardo Barros Pinto", "Douglas Cunha Sousa", "Martim Oliveira Melo", "Bianca Melo Gomes", "Carolina Sousa Castro", "Mateus Barros Ribeiro", "Evelyn Souza Sousa", "Giovana Cavalcanti Castro", "Renan Martins Pereira", "Tha�s Santos Rodrigues", "Lavinia Cardoso Castro", "Pedro Correia Cunha", "J�lio Santos Lima", "Maria Castro Ribeiro", "Igor Carvalho Melo", "Nicole Carvalho Rodrigues", "Vit�r Souza Barros", "Laura Barbosa Costa", "Manuela Carvalho Castro", "Danilo Silva Souza", "Kaua Cavalcanti Ribeiro", "Gustavo Sousa Carvalho", "J�lio Araujo Ribeiro", "Kau� Sousa Cardoso", "Emily Azevedo Rodrigues", "Vinicius Rocha Santos", "J�lia Fernandes Pinto", "Aline Almeida Alves", "Guilherme Sousa Gomes", "Matilde Sousa Goncalves", "Caio Araujo Martins", "Fernanda Costa Barros", "Nicolash Dias Cavalcanti", "Luana Rocha Cavalcanti", "Tha�s Alves Castro", "Luan Cardoso Silva", "Enzo Fernandes Almeida", "Guilherme Barbosa Rodrigues", "Ant�nio Ribeiro Goncalves", "Bianca Goncalves Araujo", "Nicole Rodrigues Cavalcanti", "Amanda Martins Silva", "Mateus Gomes Pereira", "Luiza Lima Goncalves", "Nicolas Azevedo Melo", "Camila Silva Ribeiro", "Diego Castro Cavalcanti", "Breno Ribeiro Alves", "Sophia Sousa Almeida", "Beatriz Souza Barros", "Bruna Almeida Pereira", "Vit�ria Barbosa Santos", "Laura Fernandes Barros", "Pedro Santos Castro", "Julieta Rocha Cardoso", "Eduardo Goncalves Araujo", "Rebeca Pinto Cardoso", "Vit�r Almeida Pinto", "Isabela Rodrigues Azevedo", "T�nia Cavalcanti Silva", "Raissa Rocha Silva", "Kau� Sousa Souza", "Daniel Santos Ferreira", "Joao Gomes Ribeiro", "Gabrielle Lima Rodrigues", "Laura Costa Silva", "Joao Azevedo Castro", "Kau� Barros Pereira", "Camila Gomes Santos", "Leonardo Castro Gomes", "Tiago Cardoso Rocha", "Douglas Ribeiro Cavalcanti", "Matheus Araujo Castro", "Gabriela Cardoso Pinto", "Alice Araujo Gomes", "Ot�vio Sousa Correia", "Giovanna Alves Costa", "Kau� Pereira Lima", "Lucas Lima Pinto", "Igor Almeida Correia", "F�bio Correia Araujo", "Clara Gomes Sousa", "T�nia Ribeiro Lima", "Caio Pinto Silva", "Sarah Fernandes Oliveira", "Laura Melo Araujo", "Beatriz Melo Azevedo", "Isabella Melo Goncalves", "J�lia Correia Almeida", "Luana Pinto Gomes", "Jos� Pereira Gomes", "Leonardo Barbosa Cunha", "Leonardo Oliveira Melo", "Tom�s Rodrigues Ribeiro", "Andr� Rodrigues Santos", "Bianca Costa Ferreira", "Renan Silva Costa", "Daniel Sousa Rodrigues", "Vitoria Santos Almeida", "Manuela Fernandes Ferreira", "Gabrielle Ribeiro Araujo", "Vit�ria Fernandes Oliveira", "Maria Cardoso Silva", "Carla Rodrigues Azevedo", "Nicole Carvalho Cunha", "Martim Ribeiro Almeida", "Leonor Carvalho Almeida", "Lavinia Cardoso Oliveira", "Marisa Ferreira Lima", "Douglas Correia Barbosa", "Emily Pinto Barbosa", "Leonor Cunha Araujo", "Isabela Martins Melo", "Isabela Cunha Barros", "Emily Carvalho Fernandes", "Luis Oliveira Castro", "Nicolash Dias Cardoso", "Jos� Fernandes Silva", "Camila Pinto Araujo", "Diogo Fernandes Rodrigues", "Paulo Fernandes Castro", "Tom�s Gomes Fernandes", "Eduardo Cardoso Costa", "Marina Alves Rodrigues", "Mariana Correia Fernandes", "J�lio Araujo Dias", "Sofia Barbosa Cunha", "Lu�s Barros Oliveira", "Fernanda Pereira Cunha", "Rebeca Oliveira Fernandes", "Renan Ferreira Dias", "Vit�r Goncalves Cardoso", "Vin�cius Barbosa Goncalves", "Marisa Oliveira Cunha", "Kau� Almeida Araujo", "Anna Fernandes Pereira", "Gabrielle Silva Martins", "J�lia Ferreira Araujo", "Isabelle Araujo Lima", "Laura Rodrigues Sousa", "Alex Gomes Barbosa", "F�bio Cardoso Alves", "Luis Goncalves Barbosa", "Marina Costa Rocha", "Luana Gomes Lima", "Rebeca Barros Barbosa", "Anna Pereira Sousa", "Ryan Goncalves Rocha", "Vitoria Araujo Barbosa", "Gustavo Ribeiro Fernandes", "Paulo Rodrigues Azevedo", "Julieta Rodrigues Sousa", "Marcos Dias Goncalves", "Livia Cardoso Ferreira", "Emilly Cunha Castro", "Jos� Cunha Almeida", "Murilo Carvalho Pinto", "Nicolash Lima Cavalcanti", "Amanda Fernandes Dias", "Murilo Martins Silva", "Raissa Ferreira Pinto", "J�lio Cavalcanti Santos", "Eduarda Gomes Almeida", "Alex Carvalho Melo", "Danilo Azevedo Ferreira", "J�lia Gomes Carvalho", "Tiago Ribeiro Rocha", "Victor Souza Ferreira", "Gabrielly Cardoso Carvalho", "Emily Rodrigues Santos", "Bianca Fernandes Azevedo", "Estevan Costa Martins", "Amanda Silva Sousa", "Caio Goncalves Azevedo", "Vit�ria Melo Araujo", "Camila Santos Silva", "Carolina Martins Gomes", "Jo�o Rodrigues Araujo", "Samuel Ribeiro Goncalves", "Kauan Pereira Correia", "Paulo Santos Pinto", "Kau� Gomes Sousa", "Yasmin Oliveira Souza", "Isabelle Sousa Pinto", "Carlos Souza Correia", "Marisa Cardoso Araujo", "Sarah Souza Carvalho", "Joao Oliveira Martins", "Douglas Gomes Silva", "Caio Cunha Goncalves", "Leonardo Silva Oliveira", "Leila Ribeiro Pereira", "Jos� Oliveira Cardoso", "Larissa Martins Ferreira", "Julian Ferreira Rodrigues", "Beatriz Fernandes Dias", "Laura Azevedo Almeida", "Marcos Pinto Santos", "Vinicius Cunha Almeida", "Let�cia Pereira Melo", "Rodrigo Cardoso Castro", "Jo�o Sousa Rocha", "Jo�o Cardoso Costa", "Caio Cunha Almeida", "Camila Almeida Martins", "Giovana Martins Cardoso", "Andr� Ferreira Oliveira", "Igor Alves Pinto", "Danilo Cavalcanti Azevedo", "Camila Oliveira Martins", "Let�cia Dias Cunha", "Lu�s Pereira Lima", "Gustavo Pinto Lima", "Clara Dias Almeida", "Giovanna Costa Rodrigues", "Rebeca Rocha Dias", "Rebeca Pereira Pinto", "Vitoria Barros Ferreira", "Kau� Dias Silva", "Yasmin Dias Cavalcanti", "Kauan Rocha Gomes", "Andr� Oliveira Araujo", "Livia Martins Araujo", "Luana Araujo Cunha", "Gabriel Costa Ferreira", "Camila Castro Costa", "Mariana Martins Rodrigues", "Murilo Silva Cardoso", "Gabrielly Gomes Carvalho", "Murilo Ferreira Rocha", "Bruno Gomes Correia", "Lara Pinto Santos", "Carla Carvalho Sousa", "F�bio Rocha Melo", "Carla Azevedo Cardoso", "Eduarda Almeida Melo", "Douglas Silva Almeida", "Isabelle Pereira Castro", "Laura Castro Gomes", "Sophia Cunha Silva", "Amanda Oliveira Carvalho", "Luiza Santos Almeida", "Davi Azevedo Dias", "Enzo Lima Fernandes", "Gabrielle Ferreira Rocha", "Ot�vio Ferreira Gomes", "Victor Ribeiro Almeida", "Larissa Araujo Rocha", "Kau� Pinto Carvalho", "Tom�s Cunha Almeida", "J�lia Pinto Alves", "Amanda Rocha Barbosa", "Kau� Souza Pereira", "Cau� Castro Costa", "Vin�cius Ferreira Azevedo", "Vitoria Silva Cunha", "Alex Carvalho Rocha", "Ant�nio Santos Castro", "T�nia Almeida Fernandes", "Vit�r Almeida Fernandes", "Joao Cavalcanti Oliveira", "Tha�s Barros Melo", "Renan Carvalho Ferreira", "Igor Cavalcanti Dias", "Lavinia Gomes Azevedo", "Nicolas Barbosa Ribeiro", "Rodrigo Silva Almeida", "Rafaela Goncalves Correia", "Luiza Sousa Souza", "Tha�s Cardoso Oliveira", "Matheus Pinto Rodrigues", "Bianca Lima Oliveira", "Danilo Silva Sousa", "Julian Santos Fernandes", "Laura Azevedo Carvalho", "Lu�s Alves Rodrigues", "Manuela Dias Goncalves", "Vinicius Martins Carvalho", "Tha�s Araujo Rodrigues", "J�lio Costa Rocha", "Kauan Fernandes Silva", "Carla Dias Ribeiro", "Camila Santos Martins", "Emily Azevedo Melo", "Matilde Gomes Carvalho", "Alice Sousa Azevedo", "Ant�nio Sousa Alves", "Kauan Araujo Rodrigues", "Lu�s Ribeiro Cunha", "Tiago Pinto Rodrigues", "Sofia Ferreira Carvalho", "Lara Castro Fernandes", "Caio Cardoso Lima", "Gabrielle Cardoso Correia", "Anna Rodrigues Barbosa", "Livia Santos Carvalho", "Gabrielle Azevedo Goncalves", "Alex Souza Martins", "F�bio Correia Dias", "Emilly Cardoso Cavalcanti", "Kaua Oliveira Carvalho", "Enzo Sousa Oliveira", "Douglas Pinto Silva", "Sarah Souza Fernandes", "Leila Azevedo Fernandes", "Luis Almeida Martins", "Victor Barros Lima", "Miguel Costa Araujo", "Ant�nio Barbosa Goncalves", "Diogo Silva Rocha", "F�bio Castro Gomes", "Maria Ferreira Fernandes", "Rafael Souza Castro", "Murilo Cardoso Ribeiro", "Nicolas Sousa Martins", "Jo�o Ferreira Costa", "Julieta Almeida Gomes", "Gabriela Sousa Barros", "Bruno Oliveira Barbosa", "Vitor Cavalcanti Melo", "Samuel Souza Martins", "Daniel Souza Pinto", "Brenda Dias Costa", "J�lio Rodrigues Castro", "Danilo Lima Costa", "Vin�cius Correia Gomes", "Bruno Correia Melo", "Nicolas Dias Barbosa", "Danilo Barros Souza", "Victor Correia Barros", "Renan Oliveira Goncalves", "Eduarda Pinto Sousa", "Matheus Goncalves Azevedo", "Marisa Castro Alves", "Isabella Goncalves Fernandes", "Emily Rocha Gomes", "Sarah Ribeiro Almeida", "Bruno Silva Carvalho", "Emily Araujo Ribeiro", "Diogo Goncalves Azevedo", "Camila Santos Gomes", "Samuel Ribeiro Sousa", "Vit�ria Costa Rocha", "Lu�s Costa Dias", "Maria Costa Ribeiro", "Leila Oliveira Rodrigues", "Tiago Almeida Lima", "Murilo Sousa Souza", "Luana Barbosa Souza", "Rafaela Oliveira Almeida", "Martim Silva Dias", "Samuel Barbosa Ferreira", "Evelyn Lima Fernandes"};
	private static String[] emails = {"viniciusaraujocavalcanti@dayrep.com", "marinaribeiroalmeida@teleworm.us", "saraharaujoalves@armyspy.com", "antoniocunhamartins@rhyta.com", "guilhermecunhasantos@dayrep.com", "brunaferreirapereira@jourrapide.com", "caiosouzabarbosa@teleworm.us", "eduardobarrospinto@teleworm.us", "douglascunhasousa@jourrapide.com", "martimoliveiramelo@dayrep.com", "biancamelogomes@teleworm.us", "carolinasousacastro@dayrep.com", "mateusbarrosribeiro@rhyta.com", "evelynsouzasousa@rhyta.com", "giovanacavalcanticastro@teleworm.us", "renanmartinspereira@rhyta.com", "thaissantosrodrigues@armyspy.com", "laviniacardosocastro@jourrapide.com", "pedrocorreiacunha@armyspy.com", "juliosantoslima@rhyta.com", "mariacastroribeiro@dayrep.com", "igorcarvalhomelo@teleworm.us", "nicolecarvalhorodrigues@armyspy.com", "vitorsouzabarros@dayrep.com", "laurabarbosacosta@dayrep.com", "manuelacarvalhocastro@rhyta.com", "danilosilvasouza@armyspy.com", "kauacavalcantiribeiro@jourrapide.com", "gustavosousacarvalho@dayrep.com", "julioaraujoribeiro@rhyta.com", "kauasousacardoso@teleworm.us", "emilyazevedorodrigues@jourrapide.com", "viniciusrochasantos@teleworm.us", "juliafernandespinto@dayrep.com", "alinealmeidaalves@armyspy.com", "guilhermesousagomes@jourrapide.com", "matildesousagoncalves@teleworm.us", "caioaraujomartins@teleworm.us", "fernandacostabarros@dayrep.com", "nicolashdiascavalcanti@rhyta.com", "luanarochacavalcanti@armyspy.com", "thaisalvescastro@dayrep.com", "luancardososilva@rhyta.com", "enzofernandesalmeida@jourrapide.com", "guilhermebarbosarodrigues@rhyta.com", "antonioribeirogoncalves@dayrep.com", "biancagoncalvesaraujo@teleworm.us", "nicolerodriguescavalcanti@teleworm.us", "amandamartinssilva@armyspy.com", "mateusgomespereira@rhyta.com", "luizalimagoncalves@armyspy.com", "nicolasazevedomelo@rhyta.com", "camilasilvaribeiro@jourrapide.com", "diegocastrocavalcanti@jourrapide.com", "brenoribeiroalves@jourrapide.com", "sophiasousaalmeida@teleworm.us", "beatrizsouzabarros@dayrep.com", "brunaalmeidapereira@dayrep.com", "vitoriabarbosasantos@teleworm.us", "laurafernandesbarros@jourrapide.com", "pedrosantoscastro@jourrapide.com", "julietarochacardoso@teleworm.us", "eduardogoncalvesaraujo@jourrapide.com", "rebecapintocardoso@armyspy.com", "vitoralmeidapinto@teleworm.us", "isabelarodriguesazevedo@armyspy.com", "taniacavalcantisilva@rhyta.com", "raissarochasilva@dayrep.com", "kauasousasouza@armyspy.com", "danielsantosferreira@rhyta.com", "joaogomesribeiro@jourrapide.com", "gabriellelimarodrigues@jourrapide.com", "lauracostasilva@armyspy.com", "joaoazevedocastro@dayrep.com", "kauabarrospereira@dayrep.com", "camilagomessantos@dayrep.com", "leonardocastrogomes@jourrapide.com", "tiagocardosorocha@armyspy.com", "douglasribeirocavalcanti@dayrep.com", "matheusaraujocastro@teleworm.us", "gabrielacardosopinto@dayrep.com", "alicearaujogomes@jourrapide.com", "otaviosousacorreia@jourrapide.com", "giovannaalvescosta@jourrapide.com", "kauepereiralima@rhyta.com", "lucaslimapinto@rhyta.com", "igoralmeidacorreia@armyspy.com", "fabiocorreiaaraujo@jourrapide.com", "claragomessousa@jourrapide.com", "taniaribeirolima@teleworm.us", "caiopintosilva@teleworm.us", "sarahfernandesoliveira@rhyta.com", "laurameloaraujo@jourrapide.com", "beatrizmeloazevedo@armyspy.com", "isabellamelogoncalves@rhyta.com", "juliacorreiaalmeida@armyspy.com", "luanapintogomes@teleworm.us", "josepereiragomes@armyspy.com", "leonardobarbosacunha@rhyta.com", "leonardooliveiramelo@armyspy.com", "tomasrodriguesribeiro@teleworm.us", "andrerodriguessantos@jourrapide.com", "biancacostaferreira@rhyta.com", "renansilvacosta@dayrep.com", "danielsousarodrigues@rhyta.com", "vitoriasantosalmeida@rhyta.com", "manuelafernandesferreira@jourrapide.com", "gabrielleribeiroaraujo@rhyta.com", "vitoriafernandesoliveira@dayrep.com", "mariacardososilva@dayrep.com", "carlarodriguesazevedo@rhyta.com", "nicolecarvalhocunha@teleworm.us", "martimribeiroalmeida@teleworm.us", "leonorcarvalhoalmeida@armyspy.com", "laviniacardosooliveira@armyspy.com", "marisaferreiralima@armyspy.com", "douglascorreiabarbosa@jourrapide.com", "emilypintobarbosa@jourrapide.com", "leonorcunhaaraujo@jourrapide.com", "isabelamartinsmelo@teleworm.us", "isabelacunhabarros@dayrep.com", "emilycarvalhofernandes@teleworm.us", "luisoliveiracastro@rhyta.com", "nicolashdiascardoso@teleworm.us", "josefernandessilva@rhyta.com", "camilapintoaraujo@armyspy.com", "diogofernandesrodrigues@teleworm.us", "paulofernandescastro@armyspy.com", "tomasgomesfernandes@rhyta.com", "eduardocardosocosta@jourrapide.com", "marinaalvesrodrigues@armyspy.com", "marianacorreiafernandes@armyspy.com", "julioaraujodias@dayrep.com", "sofiabarbosacunha@armyspy.com", "luisbarrosoliveira@jourrapide.com", "fernandapereiracunha@jourrapide.com", "rebecaoliveirafernandes@armyspy.com", "renanferreiradias@teleworm.us", "vitorgoncalvescardoso@rhyta.com", "viniciusbarbosagoncalves@jourrapide.com", "marisaoliveiracunha@rhyta.com", "kauaalmeidaaraujo@dayrep.com", "annafernandespereira@jourrapide.com", "gabriellesilvamartins@armyspy.com", "juliaferreiraaraujo@teleworm.us", "isabellearaujolima@jourrapide.com", "laurarodriguessousa@armyspy.com", "alexgomesbarbosa@dayrep.com", "fabiocardosoalves@jourrapide.com", "luisgoncalvesbarbosa@dayrep.com", "marinacostarocha@rhyta.com", "luanagomeslima@teleworm.us", "rebecabarrosbarbosa@dayrep.com", "annapereirasousa@armyspy.com", "ryangoncalvesrocha@teleworm.us", "vitoriaaraujobarbosa@teleworm.us", "gustavoribeirofernandes@teleworm.us", "paulorodriguesazevedo@dayrep.com", "julietarodriguessousa@armyspy.com", "marcosdiasgoncalves@jourrapide.com", "liviacardosoferreira@armyspy.com", "emillycunhacastro@jourrapide.com", "josecunhaalmeida@dayrep.com", "murilocarvalhopinto@rhyta.com", "nicolashlimacavalcanti@dayrep.com", "amandafernandesdias@jourrapide.com", "murilomartinssilva@rhyta.com", "raissaferreirapinto@rhyta.com", "juliocavalcantisantos@armyspy.com", "eduardagomesalmeida@teleworm.us", "alexcarvalhomelo@jourrapide.com", "daniloazevedoferreira@jourrapide.com", "juliagomescarvalho@teleworm.us", "tiagoribeirorocha@teleworm.us", "victorsouzaferreira@rhyta.com", "gabriellycardosocarvalho@dayrep.com", "emilyrodriguessantos@rhyta.com", "biancafernandesazevedo@dayrep.com", "estevancostamartins@teleworm.us", "amandasilvasousa@rhyta.com", "caiogoncalvesazevedo@dayrep.com", "vitoriameloaraujo@rhyta.com", "camilasantossilva@teleworm.us", "carolinamartinsgomes@armyspy.com", "joaorodriguesaraujo@armyspy.com", "samuelribeirogoncalves@teleworm.us", "kauanpereiracorreia@rhyta.com", "paulosantospinto@teleworm.us", "kauegomessousa@rhyta.com", "yasminoliveirasouza@dayrep.com", "isabellesousapinto@armyspy.com", "carlossouzacorreia@jourrapide.com", "marisacardosoaraujo@dayrep.com", "sarahsouzacarvalho@rhyta.com", "joaooliveiramartins@dayrep.com", "douglasgomessilva@dayrep.com", "caiocunhagoncalves@rhyta.com", "leonardosilvaoliveira@teleworm.us", "leilaribeiropereira@teleworm.us", "joseoliveiracardoso@rhyta.com", "larissamartinsferreira@teleworm.us", "julianferreirarodrigues@dayrep.com", "beatrizfernandesdias@teleworm.us", "lauraazevedoalmeida@teleworm.us", "marcospintosantos@rhyta.com", "viniciuscunhaalmeida@armyspy.com", "leticiapereiramelo@teleworm.us", "rodrigocardosocastro@teleworm.us", "joaosousarocha@teleworm.us", "joaocardosocosta@rhyta.com", "caiocunhaalmeida@dayrep.com", "camilaalmeidamartins@armyspy.com", "giovanamartinscardoso@armyspy.com", "andreferreiraoliveira@jourrapide.com", "igoralvespinto@armyspy.com", "danilocavalcantiazevedo@rhyta.com", "camilaoliveiramartins@dayrep.com", "leticiadiascunha@teleworm.us", "luispereiralima@rhyta.com", "gustavopintolima@jourrapide.com", "claradiasalmeida@dayrep.com", "giovannacostarodrigues@armyspy.com", "rebecarochadias@teleworm.us", "rebecapereirapinto@teleworm.us", "vitoriabarrosferreira@rhyta.com", "kauadiassilva@jourrapide.com", "yasmindiascavalcanti@jourrapide.com", "kauanrochagomes@jourrapide.com", "andreoliveiraaraujo@dayrep.com", "liviamartinsaraujo@armyspy.com", "luanaaraujocunha@rhyta.com", "gabrielcostaferreira@armyspy.com", "camilacastrocosta@jourrapide.com", "marianamartinsrodrigues@armyspy.com", "murilosilvacardoso@teleworm.us", "gabriellygomescarvalho@jourrapide.com", "muriloferreirarocha@teleworm.us", "brunogomescorreia@armyspy.com", "larapintosantos@armyspy.com", "carlacarvalhosousa@rhyta.com", "fabiorochamelo@teleworm.us", "carlaazevedocardoso@jourrapide.com", "eduardaalmeidamelo@jourrapide.com", "douglassilvaalmeida@jourrapide.com", "isabellepereiracastro@rhyta.com", "lauracastrogomes@jourrapide.com", "sophiacunhasilva@armyspy.com", "amandaoliveiracarvalho@rhyta.com", "luizasantosalmeida@rhyta.com", "daviazevedodias@jourrapide.com", "enzolimafernandes@dayrep.com", "gabrielleferreirarocha@teleworm.us", "otavioferreiragomes@teleworm.us", "victorribeiroalmeida@dayrep.com", "larissaaraujorocha@teleworm.us", "kauepintocarvalho@dayrep.com", "tomascunhaalmeida@teleworm.us", "juliapintoalves@rhyta.com", "amandarochabarbosa@jourrapide.com", "kauesouzapereira@jourrapide.com", "cauacastrocosta@jourrapide.com", "viniciusferreiraazevedo@rhyta.com", "vitoriasilvacunha@teleworm.us", "alexcarvalhorocha@dayrep.com", "antoniosantoscastro@dayrep.com", "taniaalmeidafernandes@teleworm.us", "vitoralmeidafernandes@armyspy.com", "joaocavalcantioliveira@rhyta.com", "thaisbarrosmelo@jourrapide.com", "renancarvalhoferreira@teleworm.us", "igorcavalcantidias@armyspy.com", "laviniagomesazevedo@jourrapide.com", "nicolasbarbosaribeiro@jourrapide.com", "rodrigosilvaalmeida@teleworm.us", "rafaelagoncalvescorreia@jourrapide.com", "luizasousasouza@armyspy.com", "thaiscardosooliveira@dayrep.com", "matheuspintorodrigues@rhyta.com", "biancalimaoliveira@jourrapide.com", "danilosilvasousa@teleworm.us", "juliansantosfernandes@teleworm.us", "lauraazevedocarvalho@rhyta.com", "luisalvesrodrigues@teleworm.us", "manueladiasgoncalves@dayrep.com", "viniciusmartinscarvalho@jourrapide.com", "thaisaraujorodrigues@armyspy.com", "juliocostarocha@jourrapide.com", "kauanfernandessilva@teleworm.us", "carladiasribeiro@jourrapide.com", "camilasantosmartins@dayrep.com", "emilyazevedomelo@teleworm.us", "matildegomescarvalho@teleworm.us", "alicesousaazevedo@armyspy.com", "antoniosousaalves@jourrapide.com", "kauanaraujorodrigues@armyspy.com", "luisribeirocunha@dayrep.com", "tiagopintorodrigues@teleworm.us", "sofiaferreiracarvalho@rhyta.com", "laracastrofernandes@dayrep.com", "caiocardosolima@armyspy.com", "gabriellecardosocorreia@dayrep.com", "annarodriguesbarbosa@dayrep.com", "liviasantoscarvalho@teleworm.us", "gabrielleazevedogoncalves@armyspy.com", "alexsouzamartins@dayrep.com", "fabiocorreiadias@rhyta.com", "emillycardosocavalcanti@teleworm.us", "kauaoliveiracarvalho@armyspy.com", "enzosousaoliveira@teleworm.us", "douglaspintosilva@rhyta.com", "sarahsouzafernandes@teleworm.us", "leilaazevedofernandes@armyspy.com", "luisalmeidamartins@jourrapide.com", "victorbarroslima@armyspy.com", "miguelcostaaraujo@rhyta.com", "antoniobarbosagoncalves@teleworm.us", "diogosilvarocha@dayrep.com", "fabiocastrogomes@dayrep.com", "mariaferreirafernandes@teleworm.us", "rafaelsouzacastro@jourrapide.com", "murilocardosoribeiro@jourrapide.com", "nicolassousamartins@jourrapide.com", "joaoferreiracosta@rhyta.com", "julietaalmeidagomes@teleworm.us", "gabrielasousabarros@rhyta.com", "brunooliveirabarbosa@dayrep.com", "vitorcavalcantimelo@rhyta.com", "samuelsouzamartins@teleworm.us", "danielsouzapinto@armyspy.com", "brendadiascosta@rhyta.com", "juliorodriguescastro@armyspy.com", "danilolimacosta@dayrep.com", "viniciuscorreiagomes@rhyta.com", "brunocorreiamelo@rhyta.com", "nicolasdiasbarbosa@rhyta.com", "danilobarrossouza@rhyta.com", "victorcorreiabarros@armyspy.com", "renanoliveiragoncalves@rhyta.com", "eduardapintosousa@teleworm.us", "matheusgoncalvesazevedo@rhyta.com", "marisacastroalves@rhyta.com", "isabellagoncalvesfernandes@teleworm.us", "emilyrochagomes@armyspy.com", "sarahribeiroalmeida@armyspy.com", "brunosilvacarvalho@dayrep.com", "emilyaraujoribeiro@dayrep.com", "diogogoncalvesazevedo@armyspy.com", "camilasantosgomes@teleworm.us", "samuelribeirosousa@armyspy.com", "vitoriacostarocha@dayrep.com", "luiscostadias@teleworm.us", "mariacostaribeiro@teleworm.us", "leilaoliveirarodrigues@rhyta.com", "tiagoalmeidalima@teleworm.us", "murilosousasouza@armyspy.com", "luanabarbosasouza@dayrep.com", "rafaelaoliveiraalmeida@armyspy.com", "martimsilvadias@rhyta.com", "samuelbarbosaferreira@dayrep.com", "evelynlimafernandes@teleworm.us"};
	
	public static Triple<String, String, String> getRandomTripleNameEmailGender()
	{
		String name = "";
		String email = "";
		String gender = "";
		try
		{
			URL siteURL = new URL("http://www.fakenamegenerator.com/gen-random-br-br.php");
			URLConnection siteConnection = siteURL.openConnection();
			siteConnection.setConnectTimeout(50);
			siteConnection.setReadTimeout(50);
			siteConnection.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			boolean nextIsName = false;
			try(final BufferedReader in = new BufferedReader(new InputStreamReader(siteConnection.getInputStream(), "UTF-8")))
			{
				String inputLine;
				while((inputLine = in.readLine()) != null)
				{
					if(nextIsName)
					{
						name = inputLine.substring(inputLine.indexOf("<h3>") + 4, inputLine.indexOf("</h3>"));
					}
					if(inputLine.contains("@"))
					{
						email = inputLine.substring(inputLine.indexOf("<dd>") + 4);
						email = email.substring(0, email.indexOf(" "));
					}
					if(inputLine.contains("alt=\"Female\"")) gender = "f";
					if(inputLine.contains("alt=\"Male\"")) gender = "m";
					nextIsName = inputLine.contains("<div class=\"address\">");
				}
			}
		}
		catch(IOException ex)
		{
			name = nomes[count];
			email = emails[count];
		}
		count++;
		return Triple.of(name, email, gender);
	}
}