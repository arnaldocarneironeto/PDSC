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
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.edu.ifpe.acsntrs.model.Escola;
import br.edu.ifpe.acsntrs.model.Representante;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
public class RepresentanteJpaController implements Serializable
{
    public RepresentanteJpaController(UserTransaction utx,
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

    public void create(Representante representante) throws
                                                           PreexistingEntityException,
                                                           RollbackFailureException,
                                                           Exception
    {
        if (representante.getEscolas() == null)
        {
            representante.setEscolas(new ArrayList<Escola>());
        }
        EntityManager em = null;
        try
        {
            utx.begin();
            em = getEntityManager();
            List<Escola> attachedEscolas = new ArrayList<Escola>();
            for (Escola escolasEscolaToAttach : representante.getEscolas())
            {
                escolasEscolaToAttach =
                        em.getReference(escolasEscolaToAttach.getClass(),
                                        escolasEscolaToAttach.getId());
                attachedEscolas.add(escolasEscolaToAttach);
            }
            representante.setEscolas(attachedEscolas);
            em.persist(representante);
            for (Escola escolasEscola : representante.getEscolas())
            {
                Representante oldRepresentanteOfEscolasEscola =
                        escolasEscola.getRepresentante();
                escolasEscola.setRepresentante(representante);
                escolasEscola = em.merge(escolasEscola);
                if (oldRepresentanteOfEscolasEscola != null)
                {
                    oldRepresentanteOfEscolasEscola.getEscolas().
                            remove(escolasEscola);
                    oldRepresentanteOfEscolasEscola =
                            em.merge(oldRepresentanteOfEscolasEscola);
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
            if (findRepresentante(representante.getId()) != null)
            {
                throw new PreexistingEntityException("Representante " +
                                                     representante +
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

    public void edit(Representante representante) throws IllegalOrphanException,
                                                         NonexistentEntityException,
                                                         RollbackFailureException,
                                                         Exception
    {
        EntityManager em = null;
        try
        {
            utx.begin();
            em = getEntityManager();
            Representante persistentRepresentante =
                    em.find(Representante.class, representante.getId());
            List<Escola> escolasOld = persistentRepresentante.getEscolas();
            List<Escola> escolasNew = representante.getEscolas();
            List<String> illegalOrphanMessages = null;
            for (Escola escolasOldEscola : escolasOld)
            {
                if (!escolasNew.contains(escolasOldEscola))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Escola " +
                                              escolasOldEscola +
                                              " since its representante field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Escola> attachedEscolasNew = new ArrayList<Escola>();
            for (Escola escolasNewEscolaToAttach : escolasNew)
            {
                escolasNewEscolaToAttach =
                        em.getReference(escolasNewEscolaToAttach.getClass(),
                                        escolasNewEscolaToAttach.getId());
                attachedEscolasNew.add(escolasNewEscolaToAttach);
            }
            escolasNew = attachedEscolasNew;
            representante.setEscolas(escolasNew);
            representante = em.merge(representante);
            for (Escola escolasNewEscola : escolasNew)
            {
                if (!escolasOld.contains(escolasNewEscola))
                {
                    Representante oldRepresentanteOfEscolasNewEscola =
                            escolasNewEscola.getRepresentante();
                    escolasNewEscola.setRepresentante(representante);
                    escolasNewEscola = em.merge(escolasNewEscola);
                    if (oldRepresentanteOfEscolasNewEscola != null &&
                        !oldRepresentanteOfEscolasNewEscola.equals(representante))
                    {
                        oldRepresentanteOfEscolasNewEscola.getEscolas().
                                remove(escolasNewEscola);
                        oldRepresentanteOfEscolasNewEscola =
                                em.merge(oldRepresentanteOfEscolasNewEscola);
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
                Integer id = representante.getId();
                if (findRepresentante(id) == null)
                {
                    throw new NonexistentEntityException("The representante with id " +
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
            Representante representante;
            try
            {
                representante = em.getReference(Representante.class, id);
                representante.getId();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The representante with id " +
                                                     id + " no longer exists.",
                                                     enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Escola> escolasOrphanCheck = representante.getEscolas();
            for (Escola escolasOrphanCheckEscola : escolasOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Representante (" + representante +
                                          ") cannot be destroyed since the Escola " +
                                          escolasOrphanCheckEscola +
                                          " in its escolas field has a non-nullable representante field.");
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(representante);
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

    public List<Representante> findRepresentanteEntities()
    {
        return findRepresentanteEntities(true, -1, -1);
    }

    public List<Representante> findRepresentanteEntities(int maxResults,
                                                         int firstResult)
    {
        return findRepresentanteEntities(false, maxResults, firstResult);
    }

    private List<Representante> findRepresentanteEntities(boolean all,
                                                          int maxResults,
                                                          int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Representante.class));
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

    public Representante findRepresentante(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Representante.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getRepresentanteCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Representante> rt = cq.from(Representante.class);
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
