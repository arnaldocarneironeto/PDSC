/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpe.acsntrs.jpa;

import br.edu.ifpe.acsntrs.jpa.exceptions.NonexistentEntityException;
import br.edu.ifpe.acsntrs.jpa.exceptions.PreexistingEntityException;
import br.edu.ifpe.acsntrs.jpa.exceptions.RollbackFailureException;
import br.edu.ifpe.acsntrs.model.Criterio;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.edu.ifpe.acsntrs.model.Escola;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
public class CriterioJpaController implements Serializable
{
    public CriterioJpaController(UserTransaction utx, EntityManagerFactory emf)
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

    public void create(Criterio criterio) throws PreexistingEntityException,
                                                 RollbackFailureException,
                                                 Exception
    {
        EntityManager em = null;
        try
        {
            utx.begin();
            em = getEntityManager();
            Escola escola = criterio.getEscola();
            if (escola != null)
            {
                escola = em.getReference(escola.getClass(), escola.getId());
                criterio.setEscola(escola);
            }
            em.persist(criterio);
            if (escola != null)
            {
                escola.getCriterios().add(criterio);
                escola = em.merge(escola);
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
            if (findCriterio(criterio.getId()) != null)
            {
                throw new PreexistingEntityException("Criterio " + criterio +
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

    public void edit(Criterio criterio) throws NonexistentEntityException,
                                               RollbackFailureException,
                                               Exception
    {
        EntityManager em = null;
        try
        {
            utx.begin();
            em = getEntityManager();
            Criterio persistentCriterio =
                    em.find(Criterio.class, criterio.getId());
            Escola escolaOld = persistentCriterio.getEscola();
            Escola escolaNew = criterio.getEscola();
            if (escolaNew != null)
            {
                escolaNew =
                        em.getReference(escolaNew.getClass(), escolaNew.getId());
                criterio.setEscola(escolaNew);
            }
            criterio = em.merge(criterio);
            if (escolaOld != null && !escolaOld.equals(escolaNew))
            {
                escolaOld.getCriterios().remove(criterio);
                escolaOld = em.merge(escolaOld);
            }
            if (escolaNew != null && !escolaNew.equals(escolaOld))
            {
                escolaNew.getCriterios().add(criterio);
                escolaNew = em.merge(escolaNew);
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
                Integer id = criterio.getId();
                if (findCriterio(id) == null)
                {
                    throw new NonexistentEntityException("The criterio with id " +
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

    public void destroy(Integer id) throws NonexistentEntityException,
                                           RollbackFailureException, Exception
    {
        EntityManager em = null;
        try
        {
            utx.begin();
            em = getEntityManager();
            Criterio criterio;
            try
            {
                criterio = em.getReference(Criterio.class, id);
                criterio.getId();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The criterio with id " + id +
                                                     " no longer exists.", enfe);
            }
            Escola escola = criterio.getEscola();
            if (escola != null)
            {
                escola.getCriterios().remove(criterio);
                escola = em.merge(escola);
            }
            em.remove(criterio);
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

    public List<Criterio> findCriterioEntities()
    {
        return findCriterioEntities(true, -1, -1);
    }

    public List<Criterio> findCriterioEntities(int maxResults, int firstResult)
    {
        return findCriterioEntities(false, maxResults, firstResult);
    }

    private List<Criterio> findCriterioEntities(boolean all, int maxResults,
                                                int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Criterio.class));
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

    public Criterio findCriterio(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Criterio.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getCriterioCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Criterio> rt = cq.from(Criterio.class);
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
