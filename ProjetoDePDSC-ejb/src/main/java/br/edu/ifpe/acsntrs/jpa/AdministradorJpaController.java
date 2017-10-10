/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpe.acsntrs.jpa;

import br.edu.ifpe.acsntrs.jpa.exceptions.NonexistentEntityException;
import br.edu.ifpe.acsntrs.jpa.exceptions.PreexistingEntityException;
import br.edu.ifpe.acsntrs.jpa.exceptions.RollbackFailureException;
import br.edu.ifpe.acsntrs.model.Administrador;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
public class AdministradorJpaController implements Serializable
{
    public AdministradorJpaController(UserTransaction utx,
                                      EntityManagerFactory emf)
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

    public void create(Administrador administrador) throws
                                                           PreexistingEntityException,
                                                           RollbackFailureException,
                                                           Exception
    {
        EntityManager em = null;
        try
        {
            utx.begin();
            em = getEntityManager();
            em.persist(administrador);
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
            if (findAdministrador(administrador.getId()) != null)
            {
                throw new PreexistingEntityException("Administrador " +
                                                     administrador +
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

    public void edit(Administrador administrador) throws
                                                         NonexistentEntityException,
                                                         RollbackFailureException,
                                                         Exception
    {
        EntityManager em = null;
        try
        {
            utx.begin();
            em = getEntityManager();
            administrador = em.merge(administrador);
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
                Integer id = administrador.getId();
                if (findAdministrador(id) == null)
                {
                    throw new NonexistentEntityException("The administrador with id " +
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
            Administrador administrador;
            try
            {
                administrador = em.getReference(Administrador.class, id);
                administrador.getId();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The administrador with id " +
                                                     id + " no longer exists.",
                                                     enfe);
            }
            em.remove(administrador);
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

    public List<Administrador> findAdministradorEntities()
    {
        return findAdministradorEntities(true, -1, -1);
    }

    public List<Administrador> findAdministradorEntities(int maxResults,
                                                         int firstResult)
    {
        return findAdministradorEntities(false, maxResults, firstResult);
    }

    private List<Administrador> findAdministradorEntities(boolean all,
                                                          int maxResults,
                                                          int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Administrador.class));
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

    public Administrador findAdministrador(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Administrador.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getAdministradorCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Administrador> rt = cq.from(Administrador.class);
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
