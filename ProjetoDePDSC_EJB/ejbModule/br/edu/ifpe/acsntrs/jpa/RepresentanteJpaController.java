package br.edu.ifpe.acsntrs.jpa;

import br.edu.ifpe.acsntrs.jpa.exceptions.NonexistentEntityException;
import br.edu.ifpe.acsntrs.jpa.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.edu.ifpe.acsntrs.entity.Escola;
import br.edu.ifpe.acsntrs.entity.Representante;
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
    /**
	 * 
	 */
	private static final long serialVersionUID = -40136275998246476L;

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
                                                           RollbackFailureException,
                                                           Exception
    {
        EntityManager em = null;
        try
        {
            utx.begin();
            em = getEntityManager();
            Escola escola = representante.getEscola();
            if (escola != null)
            {
                escola = em.getReference(escola.getClass(), escola.getId());
                representante.setEscola(escola);
            }
            em.persist(representante);
            if (escola != null)
            {
                escola.getRepresentantes().add(representante);
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

    public void edit(Representante representante) throws
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
            Escola escolaOld = persistentRepresentante.getEscola();
            Escola escolaNew = representante.getEscola();
            if (escolaNew != null)
            {
                escolaNew =
                        em.getReference(escolaNew.getClass(), escolaNew.getId());
                representante.setEscola(escolaNew);
            }
            representante = em.merge(representante);
            if (escolaOld != null && !escolaOld.equals(escolaNew))
            {
                escolaOld.getRepresentantes().remove(representante);
                escolaOld = em.merge(escolaOld);
            }
            if (escolaNew != null && !escolaNew.equals(escolaOld))
            {
                escolaNew.getRepresentantes().add(representante);
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

    public void destroy(Integer id) throws NonexistentEntityException,
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
            Escola escola = representante.getEscola();
            if (escola != null)
            {
                escola.getRepresentantes().remove(representante);
                escola = em.merge(escola);
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
