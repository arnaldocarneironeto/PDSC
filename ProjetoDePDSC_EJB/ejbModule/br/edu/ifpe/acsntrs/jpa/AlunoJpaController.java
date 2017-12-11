/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpe.acsntrs.jpa;

import br.edu.ifpe.acsntrs.entity.Aluno;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.edu.ifpe.acsntrs.entity.Escola;
import br.edu.ifpe.acsntrs.jpa.exceptions.NonexistentEntityException;
import br.edu.ifpe.acsntrs.jpa.exceptions.RollbackFailureException;
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

    public void create(Aluno aluno) throws RollbackFailureException, Exception
    {
        if (aluno.getPreferencia() == null)
        {
            aluno.setPreferencia(new ArrayList<Escola>());
        }
        EntityManager em = null;
        try
        {
            utx.begin();
            em = getEntityManager();
            Escola escola_que_selecionou_este_aluno =
                    aluno.getEscola_que_selecionou_este_aluno();
            if (escola_que_selecionou_este_aluno != null)
            {
                escola_que_selecionou_este_aluno =
                        em.getReference(escola_que_selecionou_este_aluno.getClass(),
                                        escola_que_selecionou_este_aluno.getId());
                aluno.setEscola_que_selecionou_este_aluno(escola_que_selecionou_este_aluno);
            }
            List<Escola> attachedPreferencia = new ArrayList<Escola>();
            for (Escola preferenciaEscolaToAttach : aluno.getPreferencia())
            {
                preferenciaEscolaToAttach =
                        em.getReference(preferenciaEscolaToAttach.getClass(),
                                        preferenciaEscolaToAttach.getId());
                attachedPreferencia.add(preferenciaEscolaToAttach);
            }
            aluno.setPreferencia(attachedPreferencia);
            em.persist(aluno);
            if (escola_que_selecionou_este_aluno != null)
            {
                escola_que_selecionou_este_aluno.getAlunos_que_preferem_esta_escola().
                        add(aluno);
                escola_que_selecionou_este_aluno =
                        em.merge(escola_que_selecionou_este_aluno);
            }
            for (Escola preferenciaEscola : aluno.getPreferencia())
            {
                preferenciaEscola.getAlunos_que_preferem_esta_escola().
                        add(aluno);
                preferenciaEscola = em.merge(preferenciaEscola);
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

    public void edit(Aluno aluno) throws NonexistentEntityException,
                                         RollbackFailureException, Exception
    {
        EntityManager em = null;
        try
        {
            utx.begin();
            em = getEntityManager();
            Aluno persistentAluno = em.find(Aluno.class, aluno.getId());
            Escola escola_que_selecionou_este_alunoOld =
                    persistentAluno.getEscola_que_selecionou_este_aluno();
            Escola escola_que_selecionou_este_alunoNew =
                    aluno.getEscola_que_selecionou_este_aluno();
            List<Escola> preferenciaOld = persistentAluno.getPreferencia();
            List<Escola> preferenciaNew = aluno.getPreferencia();
            if (escola_que_selecionou_este_alunoNew != null)
            {
                escola_que_selecionou_este_alunoNew =
                        em.getReference(escola_que_selecionou_este_alunoNew.getClass(),
                                        escola_que_selecionou_este_alunoNew.getId());
                aluno.setEscola_que_selecionou_este_aluno(escola_que_selecionou_este_alunoNew);
            }
            List<Escola> attachedPreferenciaNew = new ArrayList<Escola>();
            for (Escola preferenciaNewEscolaToAttach : preferenciaNew)
            {
                preferenciaNewEscolaToAttach =
                        em.getReference(preferenciaNewEscolaToAttach.getClass(),
                                        preferenciaNewEscolaToAttach.getId());
                attachedPreferenciaNew.add(preferenciaNewEscolaToAttach);
            }
            preferenciaNew = attachedPreferenciaNew;
            aluno.setPreferencia(preferenciaNew);
            aluno = em.merge(aluno);
            if (escola_que_selecionou_este_alunoOld != null &&
                !escola_que_selecionou_este_alunoOld.equals(escola_que_selecionou_este_alunoNew))
            {
                escola_que_selecionou_este_alunoOld.getAlunos_que_preferem_esta_escola().
                        remove(aluno);
                escola_que_selecionou_este_alunoOld =
                        em.merge(escola_que_selecionou_este_alunoOld);
            }
            if (escola_que_selecionou_este_alunoNew != null &&
                !escola_que_selecionou_este_alunoNew.equals(escola_que_selecionou_este_alunoOld))
            {
                escola_que_selecionou_este_alunoNew.getAlunos_que_preferem_esta_escola().
                        add(aluno);
                escola_que_selecionou_este_alunoNew =
                        em.merge(escola_que_selecionou_este_alunoNew);
            }
            for (Escola preferenciaOldEscola : preferenciaOld)
            {
                if (!preferenciaNew.contains(preferenciaOldEscola))
                {
                    preferenciaOldEscola.getAlunos_que_preferem_esta_escola().
                            remove(aluno);
                    preferenciaOldEscola = em.merge(preferenciaOldEscola);
                }
            }
            for (Escola preferenciaNewEscola : preferenciaNew)
            {
                if (!preferenciaOld.contains(preferenciaNewEscola))
                {
                    preferenciaNewEscola.getAlunos_que_preferem_esta_escola().
                            add(aluno);
                    preferenciaNewEscola = em.merge(preferenciaNewEscola);
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

    public void destroy(Integer id) throws NonexistentEntityException,
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
            Escola escola_que_selecionou_este_aluno =
                    aluno.getEscola_que_selecionou_este_aluno();
            if (escola_que_selecionou_este_aluno != null)
            {
                escola_que_selecionou_este_aluno.getAlunos_que_preferem_esta_escola().
                        remove(aluno);
                escola_que_selecionou_este_aluno =
                        em.merge(escola_que_selecionou_este_aluno);
            }
            List<Escola> preferencia = aluno.getPreferencia();
            for (Escola preferenciaEscola : preferencia)
            {
                preferenciaEscola.getAlunos_que_preferem_esta_escola().
                        remove(aluno);
                preferenciaEscola = em.merge(preferenciaEscola);
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
