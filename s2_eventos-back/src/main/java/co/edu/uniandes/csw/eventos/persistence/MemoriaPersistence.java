/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.EventoEntity;
import co.edu.uniandes.csw.eventos.entities.MemoriaEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Alberic Despres
 */
@Stateless
public class MemoriaPersistence {

    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;

    public MemoriaEntity create(MemoriaEntity memoria) {
        em.persist(memoria);
        return memoria;
    }
    
     public MemoriaEntity find(Long memoriasId) {
        return em.find(MemoriaEntity.class, memoriasId);
    }

    public List<MemoriaEntity> findAll() {
        TypedQuery<MemoriaEntity> query = em.createQuery("select u from MemoriaEntity u", MemoriaEntity.class);
        return query.getResultList();
    }
    
    public MemoriaEntity update(MemoriaEntity memoria) {
        return em.merge(memoria);
    }
    
    public void delete(Long memoriaId) {
        MemoriaEntity entity = em.find(MemoriaEntity.class, memoriaId);
        em.remove(entity);
    }
    
    public MemoriaEntity findByName(String name){
        TypedQuery query = em.createQuery("select u from MemoriaEntity u where u.lugar = :name", MemoriaEntity.class);
        query = query.setParameter("name", name);
        List<MemoriaEntity> sameName = query.getResultList();
        MemoriaEntity result;
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
