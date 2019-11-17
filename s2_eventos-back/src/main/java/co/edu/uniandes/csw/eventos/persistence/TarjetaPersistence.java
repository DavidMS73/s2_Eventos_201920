/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.eventos.persistence;

import co.edu.uniandes.csw.eventos.entities.TarjetaEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    private static final Logger LOGGER = Logger.getLogger(TarjetaPersistence.class.getName());

    @PersistenceContext(unitName = "eventosPU")
    protected EntityManager em;

    public TarjetaEntity create(TarjetaEntity pMedio) {
        em.persist(pMedio);
        return pMedio;
    }

    public TarjetaEntity find(Long usuarioId, Long tarjetaId) {
        LOGGER.log(Level.INFO, "Consultando la tarjeta del usuario con id = {0}", tarjetaId);
        TypedQuery<TarjetaEntity> q = em.createQuery("select p from TarjetaEntity p where(p.usuario.id = :usuarioId) and (p.id = :tarjetaId)", TarjetaEntity.class);
        q.setParameter("usuarioId", usuarioId);
        q.setParameter("tarjetaId", tarjetaId);
        
        List<TarjetaEntity> results = q.getResultList();
        TarjetaEntity tarjeta = null;
        if (results != null && !results.isEmpty()) {
            tarjeta = results.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar la tarjeta del usuario con id = {0}", tarjetaId);
        return tarjeta;
    }

    public List<TarjetaEntity> findAll() {
        TypedQuery<TarjetaEntity> q = em.createQuery("select u from TarjetaEntity u", TarjetaEntity.class);
        return q.getResultList();
    }

    public TarjetaEntity update(TarjetaEntity pNuevo) {
        LOGGER.log(Level.INFO, "Actualizando la tarjeta con id = {0}", pNuevo.getId());
        return em.merge(pNuevo);
    }

    public void delete(Long medioPagoId) {
        TarjetaEntity medio = em.find(TarjetaEntity.class, medioPagoId);
        em.remove(medio);
    }

    public TarjetaEntity findByNumber(String pNumero) {
        LOGGER.log(Level.INFO, "Consultando la tarjeta con numero = {0}", pNumero);
        
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
