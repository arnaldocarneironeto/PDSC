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
import br.edu.ifpe.acsntrs.model.Representante;
import br.edu.ifpe.acsntrs.model.Criterio;
import br.edu.ifpe.acsntrs.model.Escola;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Arnaldo Carneiro <acsn@a.recife.ifpe.edu.br>
 */
public class EscolaJpaController implements Serializable
{
    public EscolaJpaController(UserTransaction utx, EntityManagerFactory emf)
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

    public void create(Escola escola) throws PreexistingEntityException,
                                             RollbackFailureException, Exception
    {
        if (escola.getCriterios() == null)
        {
            escola.setCriterios(new ArrayList<Criterio>());
        }
        EntityManager em = null;
        try
        {
            utx.begin();
            em = getEntityManager();
            Representante representante = escola.getRepresentante();
            if (representante != null)
            {
                representante =
                        em.getReference(representante.getClass(),
                                        representante.getId());
                escola.setRepresentante(representante);
            }
            List<Criterio> attachedCriterios = new ArrayList<Criterio>();
            for (Criterio criteriosCriterioToAttach : escola.getCriterios())
            {
                criteriosCriterioToAttach =
                        em.getReference(criteriosCriterioToAttach.getClass(),
                                        criteriosCriterioToAttach.getId());
                attachedCriterios.add(criteriosCriterioToAttach);
            }
            escola.setCriterios(attachedCriterios);
            em.persist(escola);
            if (representante != null)
            {
                representante.getEscolas().add(escola);
                representante = em.merge(representante);
            }
            for (Criterio criteriosCriterio : escola.getCriterios())
            {
                Escola oldEscolaOfCriteriosCriterio =
                        criteriosCriterio.getEscola();
                criteriosCriterio.setEscola(escola);
                criteriosCriterio = em.merge(criteriosCriterio);
                if (oldEscolaOfCriteriosCriterio != null)
                {
                    oldEscolaOfCriteriosCriterio.getCriterios().
                            remove(criteriosCriterio);
                    oldEscolaOfCriteriosCriterio =
                            em.merge(oldEscolaOfCriteriosCriterio);
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
            if (findEscola(escola.getId()) != null)
            {
                throw new PreexistingEntityException("Escola " + escola +
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

    public void edit(Escola escola) throws IllegalOrphanException,
                                           NonexistentEntityException,
                                           RollbackFailureException, Exception
    {
        EntityManager em = null;
        try
        {
            utx.begin();
            em = getEntityManager();
            Escola persistentEscola = em.find(Escola.class, escola.getId());
            Representante representanteOld = persistentEscola.getRepresentante();
            Representante representanteNew = escola.getRepresentante();
            List<Criterio> criteriosOld = persistentEscola.getCriterios();
            List<Criterio> criteriosNew = escola.getCriterios();
            List<String> illegalOrphanMessages = null;
            for (Criterio criteriosOldCriterio : criteriosOld)
            {
                if (!criteriosNew.contains(criteriosOldCriterio))
                {
                    if (illegalOrphanMessages == null)
                    {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Criterio " +
                                              criteriosOldCriterio +
                                              " since its escola field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (representanteNew != null)
            {
                representanteNew =
                        em.getReference(representanteNew.getClass(),
                                        representanteNew.getId());
                escola.setRepresentante(representanteNew);
            }
            List<Criterio> attachedCriteriosNew = new ArrayList<Criterio>();
            for (Criterio criteriosNewCriterioToAttach : criteriosNew)
            {
                criteriosNewCriterioToAttach =
                        em.getReference(criteriosNewCriterioToAttach.getClass(),
                                        criteriosNewCriterioToAttach.getId());
                attachedCriteriosNew.add(criteriosNewCriterioToAttach);
            }
            criteriosNew = attachedCriteriosNew;
            escola.setCriterios(criteriosNew);
            escola = em.merge(escola);
            if (representanteOld != null &&
                !representanteOld.equals(representanteNew))
            {
                representanteOld.getEscolas().remove(escola);
                representanteOld = em.merge(representanteOld);
            }
            if (representanteNew != null &&
                !representanteNew.equals(representanteOld))
            {
                representanteNew.getEscolas().add(escola);
                representanteNew = em.merge(representanteNew);
            }
            for (Criterio criteriosNewCriterio : criteriosNew)
            {
                if (!criteriosOld.contains(criteriosNewCriterio))
                {
                    Escola oldEscolaOfCriteriosNewCriterio =
                            criteriosNewCriterio.getEscola();
                    criteriosNewCriterio.setEscola(escola);
                    criteriosNewCriterio = em.merge(criteriosNewCriterio);
                    if (oldEscolaOfCriteriosNewCriterio != null &&
                        !oldEscolaOfCriteriosNewCriterio.equals(escola))
                    {
                        oldEscolaOfCriteriosNewCriterio.getCriterios().
                                remove(criteriosNewCriterio);
                        oldEscolaOfCriteriosNewCriterio =
                                em.merge(oldEscolaOfCriteriosNewCriterio);
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
                Integer id = escola.getId();
                if (findEscola(id) == null)
                {
                    throw new NonexistentEntityException("The escola with id " +
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
            Escola escola;
            try
            {
                escola = em.getReference(Escola.class, id);
                escola.getId();
            }
            catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The escola with id " + id +
                                                     " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Criterio> criteriosOrphanCheck = escola.getCriterios();
            for (Criterio criteriosOrphanCheckCriterio : criteriosOrphanCheck)
            {
                if (illegalOrphanMessages == null)
                {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Escola (" + escola +
                                          ") cannot be destroyed since the Criterio " +
                                          criteriosOrphanCheckCriterio +
                                          " in its criterios field has a non-nullable escola field.");
            }
            if (illegalOrphanMessages != null)
            {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Representante representante = escola.getRepresentante();
            if (representante != null)
            {
                representante.getEscolas().remove(escola);
                representante = em.merge(representante);
            }
            em.remove(escola);
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

    public List<Escola> findEscolaEntities()
    {
        return findEscolaEntities(true, -1, -1);
    }

    public List<Escola> findEscolaEntities(int maxResults, int firstResult)
    {
        return findEscolaEntities(false, maxResults, firstResult);
    }

    private List<Escola> findEscolaEntities(boolean all, int maxResults,
                                            int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Escola.class));
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

    public Escola findEscola(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Escola.class, id);
        }
        finally
        {
            em.close();
        }
    }

    public int getEscolaCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Escola> rt = cq.from(Escola.class);
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
