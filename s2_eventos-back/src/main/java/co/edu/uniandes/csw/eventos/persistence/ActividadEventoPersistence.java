/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.ActividadEventoEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Germán David Martínez Solano
 */
@Stateless
public class ActividadEventoPersistence {
    
    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;

    public ActividadEventoEntity create(ActividadEventoEntity actividadEvento) {
        em.persist(actividadEvento);
        return actividadEvento;
    }

    public ActividadEventoEntity find(Long actividadEventoEventosId) {
        return em.find(ActividadEventoEntity.class, actividadEventoEventosId);
    }

    public List<ActividadEventoEntity> findAll() {
        TypedQuery<ActividadEventoEntity> query = em.createQuery("select u from ActividadEventoEntity u", ActividadEventoEntity.class);
        return query.getResultList();
    }
    
    public ActividadEventoEntity update(ActividadEventoEntity actividadEvento) {
        return em.merge(actividadEvento);
    }
    
    public void delete(Long actividadEventoId) {
        ActividadEventoEntity entity = em.find(ActividadEventoEntity.class, actividadEventoId);
        em.remove(entity);
    }
    
    public ActividadEventoEntity findByName(String name){
        TypedQuery query = em.createQuery("select u from ActividadEventoEntity u where u.nombre = :name", ActividadEventoEntity.class);
        query = query.setParameter("name", name);
        List<ActividadEventoEntity> sameName = query.getResultList();
        ActividadEventoEntity result;
        if(sameName == null) {
            result = null;
        }
        else if(sameName.isEmpty()) {
            result = null;
        }
        else{
            result = sameName.get(0);
        }
        return result;
    }
}
