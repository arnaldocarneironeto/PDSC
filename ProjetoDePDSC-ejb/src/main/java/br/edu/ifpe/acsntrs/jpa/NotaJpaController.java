/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpe.acsntrs.jpa;

import br.edu.ifpe.acsntrs.jpa.exceptions.NonexistentEntityException;
import br.edu.ifpe.acsntrs.jpa.exceptions.PreexistingEntityException;
import br.edu.ifpe.acsntrs.jpa.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.edu.ifpe.acsntrs.model.Aluno;
import br.edu.ifpe.acsntrs.model.Nota;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
public class NotaJpaController implements Serializable
{
    public NotaJpaController(UserTransaction utx, EntityManagerFactory emf)
    {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Nota nota) throws PreexistingEntityException,
                                         RollbackFailureException, Exception
    {
        EntityManager em = null;
        try
        {
            utx.begin();
            em = getEntityManager();
            Aluno aluno = nota.getAluno();
            if (aluno != null)
            {
                aluno = em.getReference(aluno.getClass(), aluno.getId());
                nota.setAluno(aluno);
            }
            em.persist(nota);
            if (aluno != null)
            {
                aluno.getNotas().add(nota);
                aluno = em.merge(aluno);
            }
            utx.commit();
        }
        catch (Exception ex)
        {
            try
            {
                utx.rollback();
            }
            catch (Exception re)
            {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.",
                                                   re);
            }
            if (findNota(nota.getId()) != null)
            {
                throw new PreexistingEntityException("Nota " + nota +
                                                     " already exists.", ex);
            }
            throw ex;
        }
        finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public void edit(Nota nota) throws NonexistentEntityException,
                                       RollbackFailureException, Exception
    {
        EntityManager em = null;
        try
        {
            utx.begin();
            em = getEntityManager();
            Nota persistentNota = em.find(Nota.class, nota.getId());
            Aluno alunoOld = persistentNota.getAluno();
            Aluno alunoNew = nota.getAluno();
            if (alunoNew != null)
            {
                alunoNew =
                        em.getReference(alunoNew.getClass(), alunoNew.getId());
                nota.setAluno(alunoNew);
            }
            nota = em.merge(nota);
            if (alunoOld != null && !alunoOld.equals(alunoNew))
            {
                alunoOld.getNotas().remove(nota);
                alunoOld = em.merge(alunoOld);
            }
            if (alunoNew != null && !alunoNew.equals(alunoOld))
            {
                alunoNew.getNotas().add(nota);
                alunoNew = em.merge(alunoNew);
            }
            utx.commit();
        }
        catch (Exception ex)
        {
            try
            {
                utx.rollback();
            }
            catch (Exception re)
            {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.",
                                                   re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                Integer id = nota.getId();
                if (findNota(id) == null)
                {
                    throw new NonexistentEntityException("The nota with id " + id +
                                                         " no longer exists.");
                }
            }
            throw ex;
        }
        finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException,
                                           RollbackFailureException, Exception
    {
        EntityManager em = null;
        try
        {
            utx.begin();
            em = getEntityManager();
            Nota nota;
            try
            {
                nota = em.getReference(Nota.class, id);
                nota.getId();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The nota with id " + id +
                                                     " no longer exists.", enfe);
            }
            Aluno aluno = nota.getAluno();
            if (aluno != null)
            {
                aluno.getNotas().remove(nota);
                aluno = em.merge(aluno);
            }
            em.remove(nota);
            utx.commit();
        }
        catch (Exception ex)
        {
            try
            {
                utx.rollback();
            }
            catch (Exception re)
            {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.",
                                                   re);
            }
            throw ex;
        }
        finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public List<Nota> findNotaEntities()
    {
        return findNotaEntities(true, -1, -1);
    }

    public List<Nota> findNotaEntities(int maxResults, int firstResult)
    {
        return findNotaEntities(false, maxResults, firstResult);
    }

    private List<Nota> findNotaEntities(boolean all, int maxResults,
                                        int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Nota.class));
            Query q = em.createQuery(cq);
            if (!all)
            {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        }
        finally
        {
            em.close();
        }
    }

    public Nota findNota(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Nota.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getNotaCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Nota> rt = cq.from(Nota.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }
        finally
        {
            em.close();
        }
    }
    
}
