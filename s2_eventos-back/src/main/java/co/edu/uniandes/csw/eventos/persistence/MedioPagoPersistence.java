/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.MedioPagoEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Estudiante
 */
@Stateless
public class MedioPagoPersistence {
    
    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;
    
    public MedioPagoEntity create(MedioPagoEntity pMedio){
        em.persist(pMedio);
        return pMedio;
    }
    
    public MedioPagoEntity find(Long medioPagoId){
        return em.find(MedioPagoEntity.class, medioPagoId);
    }
    
    public List<MedioPagoEntity> findAll(){
        TypedQuery<MedioPagoEntity> q = em.createQuery("select u from MedioPagoEntity u", MedioPagoEntity.class);
        return q.getResultList();
    }
    
    public MedioPagoEntity update(MedioPagoEntity pNuevo){
        return em.merge(pNuevo);
    }
    
    public void delete(Long medioPagoId){
        MedioPagoEntity medio = em.find(MedioPagoEntity.class, medioPagoId);
        em.remove(medio);
    }
}
