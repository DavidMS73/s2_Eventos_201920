/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.TarjetaEntity;
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
public class TarjetaPersistence {

    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;

    public TarjetaEntity create(TarjetaEntity pMedio) {
        em.persist(pMedio);
        return pMedio;
    }

    public TarjetaEntity find(Long medioPagoId) {
        return em.find(TarjetaEntity.class, medioPagoId);
    }

    public List<TarjetaEntity> findAll() {
        TypedQuery<TarjetaEntity> q = em.createQuery("select u from TarjetaEntity u", TarjetaEntity.class);
        return q.getResultList();
    }

    public TarjetaEntity update(TarjetaEntity pNuevo) {
        return em.merge(pNuevo);
    }

    public void delete(Long medioPagoId) {
        TarjetaEntity medio = em.find(TarjetaEntity.class, medioPagoId);
        em.remove(medio);
    }

    public TarjetaEntity findByNumber(String pNumero) {
        TypedQuery q = em.createQuery("select u from TarjetaEntity u where u.numeroTarjeta = :pNumero", TarjetaEntity.class);
        q = q.setParameter("pNumero", pNumero);
        List<TarjetaEntity> sameNumber = q.getResultList();
        TarjetaEntity result;
        if (sameNumber == null) {
            result = null;
        } else if (sameNumber.isEmpty()) {
            result = null;
        } else {
            result = sameNumber.get(0);
        }
        return result;
    }
}
