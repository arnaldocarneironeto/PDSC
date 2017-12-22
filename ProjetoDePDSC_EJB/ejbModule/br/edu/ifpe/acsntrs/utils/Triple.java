package br.edu.ifpe.acsntrs.utils;

/**
 *
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 * @param <L>
 * @param <M>
 * @param <R>
 */
public class Triple<L, M, R> implements Comparable<Triple<L, M, R>>
{
    private L left;
    private M middle;
    private R right;

    public static <L, M, R> Triple<L, M, R> of(L left, M middle, R right)
    {
        Triple<L, M, R> result = new Triple<>();
        result.left   = left;
        result.middle = middle;
        result.right  = right;
        return result;
    }

    public L getLeft()
    {
        return left;
    }

    public M getMiddle()
    {
        return middle;
    }

    public R getRight()
    {
        return right;
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int compareTo(Triple<L, M, R> other)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString()
    {
        return "Triple{" + "left=" + left + ", middle=" + middle + ", right=" + right + '}';
    }
}