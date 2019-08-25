/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
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
public class EventoPersistence {

    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;

    public EventoEntity create(EventoEntity evento) {
        em.persist(evento);
        return evento;
    }

    public EventoEntity find(Long eventosId) {
        return em.find(EventoEntity.class, eventosId);
    }

    public List<EventoEntity> findAll() {
        TypedQuery<EventoEntity> query = em.createQuery("select u from EventoEntity u", EventoEntity.class);
        return query.getResultList();
    }
    
    public EventoEntity update(EventoEntity evento) {
        return em.merge(evento);
    }
    
    public void delete(Long eventoId) {
        EventoEntity entity = em.find(EventoEntity.class, eventoId);
        em.remove(entity);
    }
    
    public EventoEntity findByName(String name){
        TypedQuery query = em.createQuery("select u from EventoEntity u where u.nombre = :name", EventoEntity.class);
        query = query.setParameter("name", name);
        List<EventoEntity> sameName = query.getResultList();
        EventoEntity result;
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
