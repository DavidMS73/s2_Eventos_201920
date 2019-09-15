/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.PagoEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Daniel Santiago Tenjo Leal
 */
@Stateless
public class PagoPersistence {

    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;

    public PagoEntity create(PagoEntity pago) {
        em.persist(pago);
        return pago;
    }

    public PagoEntity find(Long pagoId) {
        return em.find(PagoEntity.class, pagoId);
    }

    public List<PagoEntity> findAll() {
        TypedQuery<PagoEntity> query = em.createQuery("select u from EventoEntity u", PagoEntity.class);
        return query.getResultList();
    }

    public PagoEntity update(PagoEntity pago) {
        return em.merge(pago);
    }

    public void delete(Long pagoId) {
        PagoEntity entity = em.find(PagoEntity.class, pagoId);
        em.remove(entity);
    }

    public PagoEntity findByName(String name) {
        TypedQuery query = em.createQuery("select u from PagoEntity u where u.numeroRecibo = :name", PagoEntity.class);
        query = query.setParameter("name", name);
        List<PagoEntity> sameName = query.getResultList();
        PagoEntity result;
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
