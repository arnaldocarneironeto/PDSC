/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpe.acsntrs.jpa;

import br.edu.ifpe.acsntrs.jpa.exceptions.IllegalOrphanException;
import br.edu.ifpe.acsntrs.jpa.exceptions.NonexistentEntityException;
import br.edu.ifpe.acsntrs.jpa.exceptions.PreexistingEntityException;
import br.edu.ifpe.acsntrs.jpa.exceptions.RollbackFailureException;
import br.edu.ifpe.acsntrs.model.Aluno;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.edu.ifpe.acsntrs.model.Nota;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
public class AlunoJpaController implements Serializable
{
    public AlunoJpaController(UserTransaction utx, EntityManagerFactory emf)
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

    public void create(Aluno aluno) throws PreexistingEntityException,
                                           RollbackFailureException, Exception
    {
        if (aluno.getNotas() == null)
        {
            aluno.setNotas(new ArrayList<Nota>());
        }
        EntityManager em = null;
        try
        {
            utx.begin();
            em = getEntityManager();
            List<Nota> attachedNotas = new ArrayList<Nota>();
            for (Nota notasNotaToAttach : aluno.getNotas())
            {
                notasNotaToAttach =
                        em.getReference(notasNotaToAttach.getClass(),
                                        notasNotaToAttach.getId());
                attachedNotas.add(notasNotaToAttach);
            }
            aluno.setNotas(attachedNotas);
            em.persist(aluno);
            for (Nota notasNota : aluno.getNotas())
            {
                Aluno oldAlunoOfNotasNota = notasNota.getAluno();
                notasNota.setAluno(aluno);
                notasNota = em.merge(notasNota);
                if (oldAlunoOfNotasNota != null)
                {
                    oldAlunoOfNotasNota.getNotas().remove(notasNota);
                    oldAlunoOfNotasNota = em.merge(oldAlunoOfNotasNota);
                }
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
            if (findAluno(aluno.getId()) != null)
            {
                throw new PreexistingEntityException("Aluno " + aluno +
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

    public void edit(Aluno aluno) throws IllegalOrphanException,
                                         NonexistentEntityException,
                                         RollbackFailureException, Exception
    {
        EntityManager em = null;
        try
        {
            utx.begin();
            em = getEntityManager();
            Aluno persistentAluno = em.find(Aluno.class, aluno.getId());
            List<Nota> notasOld = persistentAluno.getNotas();
            List<Nota> notasNew = aluno.getNotas();
            List<String> illegalOrphanMessages = null;
            for (Nota notasOldNota : notasOld)
            {
                if (!notasNew.contains(notasOldNota))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Nota " +
                                              notasOldNota +
                                              " since its aluno field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Nota> attachedNotasNew = new ArrayList<Nota>();
            for (Nota notasNewNotaToAttach : notasNew)
            {
                notasNewNotaToAttach =
                        em.getReference(notasNewNotaToAttach.getClass(),
                                        notasNewNotaToAttach.getId());
                attachedNotasNew.add(notasNewNotaToAttach);
            }
            notasNew = attachedNotasNew;
            aluno.setNotas(notasNew);
            aluno = em.merge(aluno);
            for (Nota notasNewNota : notasNew)
            {
                if (!notasOld.contains(notasNewNota))
                {
                    Aluno oldAlunoOfNotasNewNota = notasNewNota.getAluno();
                    notasNewNota.setAluno(aluno);
                    notasNewNota = em.merge(notasNewNota);
                    if (oldAlunoOfNotasNewNota != null &&
                        !oldAlunoOfNotasNewNota.equals(aluno))
                    {
                        oldAlunoOfNotasNewNota.getNotas().remove(notasNewNota);
                        oldAlunoOfNotasNewNota =
                                em.merge(oldAlunoOfNotasNewNota);
                    }
                }
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
                Integer id = aluno.getId();
                if (findAluno(id) == null)
                {
                    throw new NonexistentEntityException("The aluno with id " +
                                                         id +
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

    public void destroy(Integer id) throws IllegalOrphanException,
                                           NonexistentEntityException,
                                           RollbackFailureException, Exception
    {
        EntityManager em = null;
        try
        {
            utx.begin();
            em = getEntityManager();
            Aluno aluno;
            try
            {
                aluno = em.getReference(Aluno.class, id);
                aluno.getId();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The aluno with id " + id +
                                                     " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Nota> notasOrphanCheck = aluno.getNotas();
            for (Nota notasOrphanCheckNota : notasOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Aluno (" + aluno +
                                          ") cannot be destroyed since the Nota " +
                                          notasOrphanCheckNota +
                                          " in its notas field has a non-nullable aluno field.");
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(aluno);
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

    public List<Aluno> findAlunoEntities()
    {
        return findAlunoEntities(true, -1, -1);
    }

    public List<Aluno> findAlunoEntities(int maxResults, int firstResult)
    {
        return findAlunoEntities(false, maxResults, firstResult);
    }

    private List<Aluno> findAlunoEntities(boolean all, int maxResults,
                                          int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Aluno.class));
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

    public Aluno findAluno(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Aluno.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getAlunoCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Aluno> rt = cq.from(Aluno.class);
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
