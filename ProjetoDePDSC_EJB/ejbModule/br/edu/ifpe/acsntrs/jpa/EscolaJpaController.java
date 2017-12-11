/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpe.acsntrs.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.edu.ifpe.acsntrs.entity.Representante;
import br.edu.ifpe.acsntrs.entity.Aluno;
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
public class EscolaJpaController implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4494915561167898925L;

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

    public void create(Escola escola) throws RollbackFailureException, Exception
    {
        if (escola.getAlunos_que_preferem_esta_escola() == null)
        {
            escola.setAlunos_que_preferem_esta_escola(new ArrayList<Aluno>());
        }
        if (escola.getAlunos_selecionados() == null)
        {
            escola.setAlunos_selecionados(new ArrayList<Aluno>());
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
            List<Aluno> attachedAlunos_que_preferem_esta_escola =
                    new ArrayList<Aluno>();
            for (Aluno alunos_que_preferem_esta_escolaAlunoToAttach : escola.getAlunos_que_preferem_esta_escola())
            {
                alunos_que_preferem_esta_escolaAlunoToAttach =
                        em.getReference(alunos_que_preferem_esta_escolaAlunoToAttach.getClass(),
                                        alunos_que_preferem_esta_escolaAlunoToAttach.getId());
                attachedAlunos_que_preferem_esta_escola.add(alunos_que_preferem_esta_escolaAlunoToAttach);
            }
            escola.setAlunos_que_preferem_esta_escola(attachedAlunos_que_preferem_esta_escola);
            List<Aluno> attachedAlunos_selecionados = new ArrayList<Aluno>();
            for (Aluno alunos_selecionadosAlunoToAttach : escola.getAlunos_selecionados())
            {
                alunos_selecionadosAlunoToAttach =
                        em.getReference(alunos_selecionadosAlunoToAttach.getClass(),
                                        alunos_selecionadosAlunoToAttach.getId());
                attachedAlunos_selecionados.add(alunos_selecionadosAlunoToAttach);
            }
            escola.setAlunos_selecionados(attachedAlunos_selecionados);
            em.persist(escola);
            if (representante != null)
            {
                Escola oldEscolaOfRepresentante = representante.getEscola();
                if (oldEscolaOfRepresentante != null)
                {
                    oldEscolaOfRepresentante.setRepresentante(null);
                    oldEscolaOfRepresentante =
                            em.merge(oldEscolaOfRepresentante);
                }
                representante.setEscola(escola);
                representante = em.merge(representante);
            }
            for (Aluno alunos_que_preferem_esta_escolaAluno : escola.getAlunos_que_preferem_esta_escola())
            {
                alunos_que_preferem_esta_escolaAluno.getPreferencia().
                        add(escola);
                alunos_que_preferem_esta_escolaAluno =
                        em.merge(alunos_que_preferem_esta_escolaAluno);
            }
            for (Aluno alunos_selecionadosAluno : escola.getAlunos_selecionados())
            {
                Escola oldEscola_que_selecionou_este_alunoOfAlunos_selecionadosAluno =
                        alunos_selecionadosAluno.getEscola_que_selecionou_este_aluno();
                alunos_selecionadosAluno.setEscola_que_selecionou_este_aluno(escola);
                alunos_selecionadosAluno = em.merge(alunos_selecionadosAluno);
                if (oldEscola_que_selecionou_este_alunoOfAlunos_selecionadosAluno !=
                    null)
                {
                    oldEscola_que_selecionou_este_alunoOfAlunos_selecionadosAluno.getAlunos_selecionados().
                            remove(alunos_selecionadosAluno);
                    oldEscola_que_selecionou_este_alunoOfAlunos_selecionadosAluno =
                            em.merge(oldEscola_que_selecionou_este_alunoOfAlunos_selecionadosAluno);
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

    public void edit(Escola escola) throws NonexistentEntityException,
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
            List<Aluno> alunos_que_preferem_esta_escolaOld =
                    persistentEscola.getAlunos_que_preferem_esta_escola();
            List<Aluno> alunos_que_preferem_esta_escolaNew =
                    escola.getAlunos_que_preferem_esta_escola();
            List<Aluno> alunos_selecionadosOld =
                    persistentEscola.getAlunos_selecionados();
            List<Aluno> alunos_selecionadosNew = escola.getAlunos_selecionados();
            if (representanteNew != null)
            {
                representanteNew =
                        em.getReference(representanteNew.getClass(),
                                        representanteNew.getId());
                escola.setRepresentante(representanteNew);
            }
            List<Aluno> attachedAlunos_que_preferem_esta_escolaNew =
                    new ArrayList<Aluno>();
            for (Aluno alunos_que_preferem_esta_escolaNewAlunoToAttach : alunos_que_preferem_esta_escolaNew)
            {
                alunos_que_preferem_esta_escolaNewAlunoToAttach =
                        em.getReference(alunos_que_preferem_esta_escolaNewAlunoToAttach.getClass(),
                                        alunos_que_preferem_esta_escolaNewAlunoToAttach.getId());
                attachedAlunos_que_preferem_esta_escolaNew.add(alunos_que_preferem_esta_escolaNewAlunoToAttach);
            }
            alunos_que_preferem_esta_escolaNew =
                    attachedAlunos_que_preferem_esta_escolaNew;
            escola.setAlunos_que_preferem_esta_escola(alunos_que_preferem_esta_escolaNew);
            List<Aluno> attachedAlunos_selecionadosNew = new ArrayList<Aluno>();
            for (Aluno alunos_selecionadosNewAlunoToAttach : alunos_selecionadosNew)
            {
                alunos_selecionadosNewAlunoToAttach =
                        em.getReference(alunos_selecionadosNewAlunoToAttach.getClass(),
                                        alunos_selecionadosNewAlunoToAttach.getId());
                attachedAlunos_selecionadosNew.add(alunos_selecionadosNewAlunoToAttach);
            }
            alunos_selecionadosNew = attachedAlunos_selecionadosNew;
            escola.setAlunos_selecionados(alunos_selecionadosNew);
            escola = em.merge(escola);
            if (representanteOld != null &&
                !representanteOld.equals(representanteNew))
            {
                representanteOld.setEscola(null);
                representanteOld = em.merge(representanteOld);
            }
            if (representanteNew != null &&
                !representanteNew.equals(representanteOld))
            {
                Escola oldEscolaOfRepresentante = representanteNew.getEscola();
                if (oldEscolaOfRepresentante != null)
                {
                    oldEscolaOfRepresentante.setRepresentante(null);
                    oldEscolaOfRepresentante =
                            em.merge(oldEscolaOfRepresentante);
                }
                representanteNew.setEscola(escola);
                representanteNew = em.merge(representanteNew);
            }
            for (Aluno alunos_que_preferem_esta_escolaOldAluno : alunos_que_preferem_esta_escolaOld)
            {
                if (!alunos_que_preferem_esta_escolaNew.contains(alunos_que_preferem_esta_escolaOldAluno))
                {
                    alunos_que_preferem_esta_escolaOldAluno.getPreferencia().
                            remove(escola);
                    alunos_que_preferem_esta_escolaOldAluno =
                            em.merge(alunos_que_preferem_esta_escolaOldAluno);
                }
            }
            for (Aluno alunos_que_preferem_esta_escolaNewAluno : alunos_que_preferem_esta_escolaNew)
            {
                if (!alunos_que_preferem_esta_escolaOld.contains(alunos_que_preferem_esta_escolaNewAluno))
                {
                    alunos_que_preferem_esta_escolaNewAluno.getPreferencia().
                            add(escola);
                    alunos_que_preferem_esta_escolaNewAluno =
                            em.merge(alunos_que_preferem_esta_escolaNewAluno);
                }
            }
            for (Aluno alunos_selecionadosOldAluno : alunos_selecionadosOld)
            {
                if (!alunos_selecionadosNew.contains(alunos_selecionadosOldAluno))
                {
                    alunos_selecionadosOldAluno.setEscola_que_selecionou_este_aluno(null);
                    alunos_selecionadosOldAluno =
                            em.merge(alunos_selecionadosOldAluno);
                }
            }
            for (Aluno alunos_selecionadosNewAluno : alunos_selecionadosNew)
            {
                if (!alunos_selecionadosOld.contains(alunos_selecionadosNewAluno))
                {
                    Escola oldEscola_que_selecionou_este_alunoOfAlunos_selecionadosNewAluno =
                            alunos_selecionadosNewAluno.getEscola_que_selecionou_este_aluno();
                    alunos_selecionadosNewAluno.setEscola_que_selecionou_este_aluno(escola);
                    alunos_selecionadosNewAluno =
                            em.merge(alunos_selecionadosNewAluno);
                    if (oldEscola_que_selecionou_este_alunoOfAlunos_selecionadosNewAluno !=
                        null &&
                        !oldEscola_que_selecionou_este_alunoOfAlunos_selecionadosNewAluno.equals(escola))
                    {
                        oldEscola_que_selecionou_este_alunoOfAlunos_selecionadosNewAluno.getAlunos_selecionados().
                                remove(alunos_selecionadosNewAluno);
                        oldEscola_que_selecionou_este_alunoOfAlunos_selecionadosNewAluno =
                                em.merge(oldEscola_que_selecionou_este_alunoOfAlunos_selecionadosNewAluno);
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

    public void destroy(Integer id) throws NonexistentEntityException,
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
            Representante representante = escola.getRepresentante();
            if (representante != null)
            {
                representante.setEscola(null);
                representante = em.merge(representante);
            }
            List<Aluno> alunos_que_preferem_esta_escola =
                    escola.getAlunos_que_preferem_esta_escola();
            for (Aluno alunos_que_preferem_esta_escolaAluno : alunos_que_preferem_esta_escola)
            {
                alunos_que_preferem_esta_escolaAluno.getPreferencia().
                        remove(escola);
                alunos_que_preferem_esta_escolaAluno =
                        em.merge(alunos_que_preferem_esta_escolaAluno);
            }
            List<Aluno> alunos_selecionados = escola.getAlunos_selecionados();
            for (Aluno alunos_selecionadosAluno : alunos_selecionados)
            {
                alunos_selecionadosAluno.setEscola_que_selecionou_este_aluno(null);
                alunos_selecionadosAluno = em.merge(alunos_selecionadosAluno);
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
