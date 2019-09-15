/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.PseEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Daniel Santiago Tenjo
 */
@Stateless
public class PsePersistence {

    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;

    public PseEntity create(PseEntity pse) {
        em.persist(pse);
        return pse;
    }

    public PseEntity find(Long PseId) {
        return em.find(PseEntity.class, PseId);
    }

    public List<PseEntity> findAll() {
        TypedQuery<PseEntity> query = em.createQuery("select u from EventoEntity u", PseEntity.class);
        return query.getResultList();
    }

    public PseEntity update(PseEntity evento) {
        return em.merge(evento);
    }

    public void delete(Long eventoId) {
        PseEntity entity = em.find(PseEntity.class, eventoId);
        em.remove(entity);
    }

    public PseEntity findByName(String name) {
        TypedQuery query = em.createQuery("select u from EventoEntity u where u.nombre = :name", PseEntity.class);
        query = query.setParameter("name", name);
        List<PseEntity> sameName = query.getResultList();
        PseEntity result;
        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }
        return result;
    }

}
