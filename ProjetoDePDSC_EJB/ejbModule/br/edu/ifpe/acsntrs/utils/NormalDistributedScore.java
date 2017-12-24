 package br.edu.ifpe.acsntrs.utils;

/**
 * Gera notas de alunos segundo uma distribuição normal. Os dados usados
 * para a definição da distribuição foram encontrados em
 * 
 * https://repositorio.unesp.br/bitstream/handle/11449/91939/bortolotti_gmf_me_rcla.pdf?sequence=1
 * 
 * e foram escolhidos por representar uma distribuição que parecia mais
 * natural.
 * 
 * @author Arnaldo
 *
 */
public class NormalDistributedScore
{
	public static Float next()
	{
		return SCORES.next();
	}
	
	private static final RandomCollection<Float> SCORES;
	
	static
	{
		SCORES = new RandomCollection<>();
		SCORES.add(6, 0f);
        SCORES.add(6, 0.5f);
        SCORES.add(6, 1f);
        SCORES.add(9, 1.5f);
        SCORES.add(12, 2f);
        SCORES.add(20, 2.5f);
        SCORES.add(44, 3f);
        SCORES.add(104, 3.5f);
        SCORES.add(272, 4f);
        SCORES.add(568, 4.5f);
        SCORES.add(980, 5f);
        SCORES.add(1476, 5.5f);
        SCORES.add(1736, 6f);
        SCORES.add(1692, 6.5f);
        SCORES.add(1340, 7f);
        SCORES.add(868, 7.5f);
        SCORES.add(452, 8f);
        SCORES.add(208, 8.5f);
        SCORES.add(84, 9f);
        SCORES.add(36, 9.5f);
        SCORES.add(16, 10f);
	}
}
