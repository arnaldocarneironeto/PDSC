 package br.edu.ifpe.acsntrs.utils;

/**
 * Gera conceitos das escolas segundo uma distribuição normal. Os dados usados
 * para a definição da distribuição foram encontrados em
 * 
 * https://repositorio.unesp.br/bitstream/handle/11449/91939/bortolotti_gmf_me_rcla.pdf?sequence=1
 * 
 * e foram escolhidos por representar uma distribuição que parecia mais natural.
 * 
 * @author Arnaldo
 *
 */
public class NormalDistributedGrade
{
	public static String next()
	{
		return SCORES.next();
	}
	
	private static final RandomCollection<String> SCORES;
	
	static
	{
		SCORES = new RandomCollection<>();
        SCORES.add(12, "F");
        SCORES.add(20, "F+");
        SCORES.add(44, "E-");
        SCORES.add(104, "E");
        SCORES.add(272, "E+");
        SCORES.add(568, "D-");
        SCORES.add(980, "D");
        SCORES.add(1476, "D+");
        SCORES.add(1736, "C-");
        SCORES.add(1692, "C");
        SCORES.add(1340, "C+");
        SCORES.add(868, "B-");
        SCORES.add(452, "B");
        SCORES.add(208, "B+");
        SCORES.add(84, "A-");
        SCORES.add(36, "A");
	}
}
